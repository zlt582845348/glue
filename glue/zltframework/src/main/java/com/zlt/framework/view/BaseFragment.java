
package com.zlt.framework.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    protected View baseView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        baseView = inflater.inflate(getBaseLayoutId(), null);
        initView();
        initListener();
        initData();
        return baseView;
    }

    protected abstract int getBaseLayoutId();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData();

    /**
     * 展示toast
     * @param msg
     */
    protected void showToast(String msg)
    {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 设置view的单击监听
     * @param id view的id
     */
    protected void setClickListener(int id)
    {
        if(baseView==null)
        {
            return;
        }
        View view = baseView.findViewById(id);
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
}
