package com.zlt.framework.reflash;

import android.view.View;

/**
 * Created by Administrator on 2017/6/6.
 */

public interface IRefreshView {

    public void drag();

    public void loading();

    public void end();

    public void setHaveMore(boolean have);

    public boolean haveMore();
}
