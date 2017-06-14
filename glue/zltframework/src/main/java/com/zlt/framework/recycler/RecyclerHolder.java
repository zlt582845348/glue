package com.zlt.framework.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zlt.framework.application.ZltApplication;
import com.zlt.framework.util.ViewUtil;

import java.util.HashMap;

/**
 * Author: zlt
 * Date: 2016-07-12
 * Time: 14:49
 * RecyclerView的holder
 */
public class RecyclerHolder extends RecyclerView.ViewHolder {

    private static final String DATA_TAG = "tag";
    private HashMap<String,Object> datas = new HashMap<>();

    public RecyclerHolder(View itemView) {
        super(itemView);
    }

    public <V extends View> V getView(String resName)
    {
        int viewId = ViewUtil.getResId(ZltApplication.getAppContext(),"id",resName);
        return (V)itemView.findViewById(viewId);
    }

    public <V extends View> V getView(int resId)
    {
        return (V)itemView.findViewById(resId);
    }

    public void setData(Object data)
    {
        datas.put(DATA_TAG,data);
    }

    public <O extends Object> O getData()
    {
        return (O)datas.get(DATA_TAG);
    }

    public void setData(String key, Object data)
    {
        datas.put(key,data);
    }

    public <O extends Object> O getData(String key)
    {
        return (O)datas.get(key);
    }

}
