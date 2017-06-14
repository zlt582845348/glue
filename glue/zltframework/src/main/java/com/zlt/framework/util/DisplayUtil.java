
package com.zlt.framework.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class DisplayUtil {

    public static int mScreenHeight = -1;
    public static int mScreenWidth = -1;

    /**
     * 获取手机屏幕高度,以px为单位
     * 
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context)
    {
        if (mScreenHeight <= 0)
        {
            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager manager = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            manager.getDefaultDisplay().getMetrics(metrics);
            // return metrics.heightPixels;
            mScreenHeight = metrics.heightPixels;
        }
        return mScreenHeight;
    }

    /**
     * 获取手机屏幕宽度，以px为单位
     * 
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context)
    {
        if (mScreenWidth <= 0)
        {
            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager manager = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            manager.getDefaultDisplay().getMetrics(metrics);
            // return metrics.widthPixels;
            mScreenWidth = metrics.widthPixels;
        }
        return mScreenWidth;
    }

    /**
     * 返回程序window宽度
     * 
     * @return
     */
    public static int getWindowWidth(Activity activity) {
        return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getWidth();
    }

    /**
     * 返回程序window高度，不包括通知栏和标题栏
     * 
     * @return
     */
    public static int getWindowContentHeight(Activity activity) {
        return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT)
                .getHeight();
    }

    /**
     * 返回程序window高度，不包括通知栏
     * 
     * @return
     */
    public static int getWindowHeight(Activity activity) {
        return getScreenHeight(activity) - getStatusBarHeight(activity);
    }

    /**
     * 返回屏幕像素密度
     * 
     * @param context
     * @return
     */
    public static float getPixelDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 返回状态栏高度
     * 
     * @param activity
     * @return
     */
    public static int getStatusBarHeight(Activity activity) {
        Rect outRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        return outRect.top;
    }

    /**
     * 上面那个取不到,这个能取到状态栏高度
     */
    public static int getStatusBarHeight() {
        return Resources.getSystem().getDimensionPixelSize(
                Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android"));
    }

    public static int getTitleBarHeight(Activity activity) {
        return getScreenHeight(activity) - getWindowContentHeight(activity)
                - getStatusBarHeight(activity);
    }

    /**
     * 单位转换，将dip转换为px
     * 
     * @param dp
     * @param context
     * @return
     */
    public static int dip2px(float dp, Context context) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 单位转换，将px转换为dip
     * 
     * @param px
     * @param context
     * @return
     */
    public static int px2dip(float px, Context context) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

}
