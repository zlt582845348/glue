package com.zlt.framework.core.featrues;

/**
 * Created by Administrator on 2017/3/21.
 */

public interface IHttpCallback extends IBase {

    public void success(String result,String tag);
    public void failed(String msg,String tag,Exception ex);
}
