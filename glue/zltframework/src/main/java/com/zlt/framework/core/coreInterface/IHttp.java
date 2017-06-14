package com.zlt.framework.core.coreInterface;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/20.
 */

public interface IHttp extends IBase {

    public void get(String url,IHttpCallback callback);

    public void get(String url,Object tag,IHttpCallback callback);

    public void get(String url, HashMap<String,Object> params, IHttpCallback callback);

    public void get(String url,HashMap<String,Object> params,Object tag,IHttpCallback callback);


    public void post(String url,IHttpCallback callback);

    public void post(String url,Object tag,IHttpCallback callback);

    public void post(String url, HashMap<String,Object> params, IHttpCallback callback);

    public void post(String url,HashMap<String,Object> params,Object tag,IHttpCallback callback);


    public void putFile(String path, String url, IHttpCallback callback);

    public void putFile(File file, String url, IHttpCallback callback);

}
