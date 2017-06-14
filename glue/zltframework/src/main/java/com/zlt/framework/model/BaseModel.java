package com.zlt.framework.model;

import com.google.gson.reflect.TypeToken;
import com.zlt.framework.bean.IBean;
import com.zlt.framework.core.CoreManager;
import com.zlt.framework.core.coreInterface.IHttpCallback;
import com.zlt.framework.presenter.BasePresenter;
import com.zlt.framework.presenter.IPresenter;
import com.zlt.framework.view.IView;

/**
 * Created by Administrator on 2017/3/22.
 */

public class BaseModel implements IModel {

    protected IBean bean = null;
    protected IPresenter.PCall call;

    public static  <T extends BaseModel> T getInstance(Class<T> cls,IPresenter.PCall call) {
        T m = null;
        try {
            m = cls.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if(m!=null)
        {
            ((BaseModel)m).call = call;
        }
        return m;
    }

    @Override
    public <T extends IBean> T getBean() {
        return (T)bean;
    }

    @Override
    public String getJson() {
        return CoreManager.getJson().getJson(bean);
    }

}
