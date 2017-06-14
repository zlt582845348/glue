package com.zlt.framework.qrcode;

import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.view.SurfaceHolder;

import com.google.zxing.Result;
import com.zlt.framework.qrcode.zxing.view.ViewfinderView;

public interface MipcaCapture  {

	/**
	 * 处理扫描结果
	 * @param result
	 * @param barcode
	 */
	public void handleDecode(Result result, Bitmap barcode);
	
	public void initCamera(SurfaceHolder surfaceHolder);

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
							   int height) ;
	

	public ViewfinderView getFinderView();

	public Handler getHandler();

	public void drawViewfinder();

	public void initBeepSound();

	public void playBeepSoundAndVibrate();

	public static final long VIBRATE_DURATION = 200L;

	public final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

}
