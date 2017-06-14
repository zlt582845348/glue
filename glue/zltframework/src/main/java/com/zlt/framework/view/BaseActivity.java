
package com.zlt.framework.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

/**
 * activity基类
 *
 * @author zlt
 *         create at 2015/12/3 15:56
 */
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {

    public static final String EXIT_APP = "ExitApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在当前的activity中注册APP退出广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(EXIT_APP);
        this.registerReceiver(this.broadcastReceiver, filter);
        //设置界面布局
        setContentView(getBaseLayoutId());
        //初始化界面
        initView();
        //初始化监听
        initListener();
        //初始化数据
        initData();
    }

    /**
     * 设置界面布局
     * @return
     */
    protected abstract int getBaseLayoutId();
    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化控件监听
     */
    protected abstract void initListener();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 展示toast
     *
     * @param msg
     */
    protected void showToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    /**
     * 设置view的单击监听
     * @param id view的id
     */
    protected void setClickListener(int id)
    {
        View view = findViewById(id);
        if(view!=null)
        {
            view.setOnClickListener(this);
        }
    }
    /**
     * 设置view的单击监听
     * @param view
     */
    protected void setClickListener(View view)
    {
        if(view!=null)
        {
            view.setOnClickListener(this);
        }
    }
    /**
     * 退出应用程序
     * 通过android广播方式 关闭所有activity
     * 所有activity必须继承该类
     */
    protected void exit() {
        Intent intent = new Intent();
        intent.setAction("ExitApp");
        this.sendBroadcast(intent);
        super.finish();
    }

    //退出程序广播监听
    protected BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
           switch (intent.getAction()){
               case EXIT_APP:
                   finish();
                   break;
               default:
                   break;
           }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消退出应用监听
        this.unregisterReceiver(this.broadcastReceiver);
    }
}
