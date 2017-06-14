package com.zlt.framework.presenter;

import com.zlt.framework.view.IView;

/**
 * Created by Administrator on 2017/3/22.
 */

public class BasePresenter implements IPresenter,IPresenter.PCall {

    private IView call;

    public static <T extends BasePresenter> T getInstance(Class<T> cls,IView call)
    {
        T p = null;
        try {
            p = cls.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if(p!=null)
        {
            ((BasePresenter)p).call = call;
        }

        return p;
    }

    @Override
    public void setVCall(IView call) {
        this.call = call;
    }

    @Override
    public void toView(String tag, int state, Object... objs) {
        if(call!=null)
        {
            call.call(tag, state, objs);
        }
    }

    @Override
    public void call(String tag, int state, Object... objs) {
        toView( tag, state,  objs);
    }
}
