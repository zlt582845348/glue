package com.zlt.framework.core.coreInterface;

/**
 * Created by Administrator on 2017/3/20.
 */

public interface ILog extends IBase {

    public void d(Object msg);

    public void e(String msg);

    public void w(String msg);

    public void v(String msg);

    public void json(String msg);

}
