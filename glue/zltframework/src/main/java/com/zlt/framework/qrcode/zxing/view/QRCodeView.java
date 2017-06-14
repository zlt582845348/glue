package com.zlt.framework.qrcode.zxing.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.zlt.framework.R;
import com.zlt.framework.core.CoreManager;
import com.zlt.framework.qrcode.MipcaCapture;
import com.zlt.framework.qrcode.zxing.camera.CameraManager;
import com.zlt.framework.qrcode.zxing.decoding.CaptureActivityHandler;
import com.zlt.framework.qrcode.zxing.decoding.InactivityTimer;
import com.zlt.framework.util.ViewUtil;

import java.io.IOException;
import java.util.Vector;

/**
 * Author: zlt
 * Date: 2016-07-06
 * Time: 14:48
 * 扫码控件
 */
public class QRCodeView extends FrameLayout implements SurfaceHolder.Callback, MipcaCapture {

    protected int codeType = -1;      //标示 (1一维码、 2、二维码   3、其他码)
    public static final int CODE_BAR = 1;      //一维码
    public static final int CODE_QR = 2;      //二维码
    public static final int CODE_OTHER = 3;   //其他码

    private Context context;
    private CaptureActivityHandler handler;
    private FinderView finderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;

    private float scanWigth = 0.7f;
    private float scanHeight = 0.7f;

    private OnQrcodeResultLisenter lisenter;

    public QRCodeView(Context context) {
        super(context);
        initView(context, null);
    }

    public QRCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public QRCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    /**
     * 初始化界面
     */
    private void initView(Context context, AttributeSet attrs)
    {
        this.context = context;
        if(attrs!=null)
        {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.qrcode);
            String wStr = array.getString(R.styleable.qrcode_qrcode_width);
            String hStr = array.getString(R.styleable.qrcode_qrcode_height);
            try {
                if(wStr!=null&&!wStr.equals(""))
                {
                    scanWigth = Float.parseFloat(wStr.substring(0,wStr.length()-1))/100;
                }
                if(hStr!=null&&!hStr.equals(""))
                {
                    scanHeight = Float.parseFloat(hStr.substring(0,hStr.length()-1))/100;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(ViewUtil.getResId(context, "layout", "view_capture"),null);
        this.addView(view);
        CameraManager.init(context);
        finderView = (FinderView) findViewById(ViewUtil.getResId(context, "id","viewfinder_view"));
        finderView.setPercentSize(scanWigth,scanHeight);
        hasSurface = false;
        inactivityTimer = new InactivityTimer((Activity)context);


        //////
        SurfaceView surfaceView = (SurfaceView) findViewById(ViewUtil.getResId(context, "id","preview_view"));
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) context.getSystemService(Activity.AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;

    }

    /**
     * 继续扫码
     */
    public void continueScan()
    {
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        initCamera(surfaceHolder);
        if (handler != null){
            handler.restartPreviewAndDecode();
            codeType = -1;
        }
    }

    public void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    public void onDestroy() {
        inactivityTimer.shutdown();
    }

    /**
     * 处理扫描结果
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        if (resultString.equals("")) {
            Toast.makeText(context, "Scan failed!", Toast.LENGTH_SHORT).show();
            if(lisenter!=null)
            {
                lisenter.onResult(this,false,codeType,resultString,barcode);
            }
        }else {
            Toast.makeText(context, resultString, Toast.LENGTH_SHORT).show();
            if(lisenter!=null)
            {
                //扫描获取的 编码 不为空
                if(!TextUtils.isEmpty(result.getBarcodeFormat().toString())){
                    //拍码后返回的编码格式
                    String mBarcodeFormat = result.getBarcodeFormat().toString();
                    if(mBarcodeFormat.equals("DATA_MATRIX")){
                        codeType = CODE_OTHER;
                    }else if(mBarcodeFormat.equals("QR_CODE")){
                        codeType = CODE_QR;
                    }else {
                        codeType = CODE_BAR;
                    }
                    CoreManager.getLog().e("---> (1一维码、  2、二维码    3、其他码) " + codeType);
                }
                lisenter.onResult(this,true,codeType,resultString,barcode);
            }
        }

    }

    public void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler((Activity)context,this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getFinderView() {
        return finderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        finderView.drawViewfinder();

    }

    public void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            ((Activity)context).setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    ViewUtil.getResId(context, "raw","beep"));
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    public void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) context.getSystemService(Activity.VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    public interface OnQrcodeResultLisenter{
        public void onResult(QRCodeView view, boolean success, int type, String result, Bitmap barcode);
    }

    public void setOnQrcodeResultLisenter(OnQrcodeResultLisenter lisenter)
    {
        this.lisenter = lisenter;
    }
}
