package com.zlt.framework.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/30.
 */

public class FileUtil {

    /**
     * 图片保存
     * 默认图片名为当前时间
     * @param bitmap
     */
    public static String saveBitmap(Context context, Bitmap bitmap, String basePath, Bitmap.CompressFormat compressFormat) {
        Date date = new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
        String savePath = saveBitmap( context, bitmap,  basePath,  dateFormat.format(date)+"."+compressFormat.name().toLowerCase(), compressFormat);
        return savePath;
    }

    /**
     * 图片保存
     * 保存后刷新相册
     * @param bitmap
     */
    public static String saveBitmap(Context context,Bitmap bitmap, String basePath, String fileName,Bitmap.CompressFormat compressFormat) {

        //判断文件夹是否存在,如果不存在则创建文件夹
        String dirName = Environment.getExternalStorageDirectory().getPath() + File.separator + basePath;
        File target = createFile( dirName, fileName);
        //图片写入
        try {
            FileOutputStream out = new FileOutputStream(target);
            bitmap.compress(compressFormat, 90, out);
            out.flush();
            out.close();
            // Log.i(TAG, "已经保存");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            return null;
        }
        //相册扫描该文件 否则相册不能显示该图片
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + dirName + File.separator + fileName)));
        return dirName + File.separator + fileName;
    }
    /**
     * 创建本地文件
     * 存在时删除再创建 不存在时直接创建
     * 需要sd卡读写权限
     * @param dirName 文件夹路径
     * @param fileName 文件名称（包括扩展名）
     * @return
     */
    public static File createFile(String dirName,String fileName)
    {
        File f = new File(dirName);
        if(!f.exists())
        {
            boolean ok = f.mkdirs();
            if(!ok)
            {
                return null;
            }
        }
        //创建文件
        File target = new File(dirName, fileName);
        try {
            if(target.exists())
            {
                target.delete();
            }
            boolean ok = target.createNewFile();
            if(!ok)
            {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return target;
    }
}
