
package com.zlt.framework.application;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ZltApplication extends Application {

    private static String mEnterTime = "";

    public static List<Integer> mListPosition = new ArrayList<Integer>();
    private static final String imgPath = Environment.getExternalStorageDirectory() + "/mftour/imagecache";
    /**
     * 全局Context，原理是因为Application类是应用最先运行的，所以在我们的代码调用时，该值已经被赋值过了
     */
    private static ZltApplication sContext;

    /**
     * 主线程ID
     */
    private static int mMainThreadId = -1;
    /**
     * 主线程ID
     */
    private static Thread mMainThread;
    /**
     * 主线程Handler
     */
    private static Handler mMainThreadHandler;
    /**
     * 主线程Looper
     */
    private static Looper mMainLooper;
    private String mVersion = null;
    private Handler mHandler = new Handler();
    public static ExecutorService FULL_TASK_EXECUTOR = (ExecutorService) Executors
            .newCachedThreadPool();

    @Override
    public void onCreate() {
        super.onCreate();
        // 捕获全局异常
        sContext = this;
        mMainThreadId = android.os.Process.myTid();
        mMainThread = Thread.currentThread();
        mMainThreadHandler = new Handler();
        mMainLooper = getMainLooper();
    }

    class MyHandler implements UncaughtExceptionHandler {
        // 一旦某个异常未被捕获,就走到此方法中
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            // 根据当前进程id,杀死进程
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    public static Context getAppContext() {
        return sContext;
    }

    public Handler getHandler() {
        return mHandler;
    }

    public void applicationExit() {
        System.exit(0);
    }

    public static List<Integer> getYiDianWeiDuDePosition(int position) {

        mListPosition.add(position);

        return mListPosition;
    }

    public static String getEntraterTime(String time) {

        mEnterTime += time;
        return mEnterTime;

    }

    public int getMaxSize() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / (1024 * 1024));
        return maxMemory;
    }


    /**
     * 获取主线程ID
     */
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    /**
     * 获取主线程
     */
    public static Thread getMainThread() {
        return mMainThread;
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    /**
     * 获取主线程的looper
     */
    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }


}
