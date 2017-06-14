package com.zlt.framework.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import java.util.concurrent.atomic.AtomicInteger;

public class ViewUtil {
	private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
	/**
	 * Generate a value suitable for use in
	 * This value will not collide with ID values generated at build time by aapt for R.id.
	 *
	 * @return a generated ID value
	 */
	private static int generateViewId() {
	    for (;;) {
	        final int result = sNextGeneratedId.get();
	        // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
	        int newValue = result + 1;
	        if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
	        if (sNextGeneratedId.compareAndSet(result, newValue)) {
	            return result;
	        }
	    }
	}
	/**
	 * 获取view Id(适合动态创建的view)
	 * @return
	 */
	public static int getViewId()
	{
		int id = 0;
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
			id = generateViewId();
	    } else {
	    	id = getView17Id();
	    }
		return id;
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	private static int getView17Id()
	{
		int id = View.generateViewId();
		return id;
	}
	
	/**
	 * 获取屏幕密度
	 * 
	 * @param context
	 */
	public static float getScreenDensity(Context context) {
		WindowManager wm = (WindowManager) context.getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metric = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metric);
		return metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
		
	}

	/**
	 * 根据资源名称和资源类型 获取资源id
	 * @param context 上下文
	 * @param resType 资源类型 "drawable" "id"等
	 * @param resName 资源名称
	 * @return
	 */
	public static int getResId(Context context,String resType,String resName)
	{
		Resources res=context.getResources();  
		int i=res.getIdentifier(resName,resType,context.getPackageName());  
		return i;
	}
}
