package com.zlt.framework.core.featrues;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/3/20.
 */

public interface IJson extends IBase {

    public String getJson(Object object);
    public <T extends Object> T getModel(String json,Class<T> cls);
    public <T extends Object> T getModel(String json,Type type);
}
