package com.zlt.framework.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;

import com.zlt.framework.application.ZltApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/3/27.
 */

public class BitmapUtil {

    /**
     * （默认尺寸压缩）按比例等比例压缩 压缩到默认长宽(屏幕长宽的二分之一)
     * @param bm
     * @return
     */
    public static Bitmap reduce(Bitmap bm)
    {
        float w = DisplayUtil.getScreenWidth(ZltApplication.getAppContext()) /2;
        float h = DisplayUtil.getScreenHeight(ZltApplication.getAppContext()) /2;
        return reduce(bm,w, h);
    }

    /**
     * （尺寸压缩）按比例等比例压缩
     * @param bm
     * @param width
     * @param height
     * @return
     */
    public static Bitmap reduce(Bitmap bm,float width, float height)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int w = bm.getWidth();
        int h = bm.getHeight();
        bm.recycle();
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h&&w > width) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (w / width);
        } else if (w < h&&h > height) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (h / height);
        }
        if (be <= 0)
            be = 1;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = be;//设置缩放比例
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, opts);
        return bitmap;
    }
    /**
     * 质量压缩
     * @param bm
     * @return
     */
    public static Bitmap compress(Bitmap bm,int size) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while ( baos.toByteArray().length / 1024>size) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            bm.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
        }
        bm.recycle();
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }
    /**
     * 默认质量压缩（默认压缩到小于200K）
     * @param bm
     * @return
     */
    public static Bitmap compress(Bitmap bm) {
        return compress(bm, 200);
    }

    /**
     * 方向矫正
     * @param bm
     */
    public static Bitmap orientationRedress(Context context, Bitmap bm, Uri uri)
    {
        int rotate = 0;
        String path = DeviceUtil.getRealPathFromURI(context, uri);
        if(path==null)
        {
            //未获取到图片的uri 返回原图 方向矫正失败
            return bm;
        }
        //偏移角度
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int result = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            switch(result) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                default:
                    break;
            }

        } catch (IOException e1) {
            return bm;
        }
        //矫正
        if(rotate!=0)
        {
            // 旋转图片
            bm = rotate(bm, rotate);
        }
        return bm;
    }
    /**
     * 图片旋转
     * @param bitmap
     * @param rotate
     * @return
     */
    public static Bitmap rotate(Bitmap bitmap,float rotate)
    {
        // 旋转图片
        Matrix m = new Matrix();
        m.postRotate(rotate);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), m, true);
        return bitmap;
    }
    /**
     * drawable转Bitmap
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * bitmap转字节流
     * @param bm
     * @return
     */
    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
    /**
     * 字节流转bitmap
     * @param b
     * @return
     */
    public static Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

}
