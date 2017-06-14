package com.zlt.framework.core.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zlt.framework.core.featrues.IJson;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/3/21.
 */

public class GsonImpl implements IJson {

    @Override
    public String getJson(Object object) {
        if(object == null ||object.equals(""))
        {
            return "";
        }
        String str = getGson().toJson(object);
        return str;
    }

    @Override
    public <T extends Object> T getModel(String json, Class<T> cls) {
        return getGson().fromJson(json, cls);
    }

    @Override
    public <T extends Object> T getModel(String json, Type type) {
        T t = getGson().fromJson(json, type);
        return t;
    }

    private static Gson gson;
    /**
     * 获取Gson
     * @return
     */
    private static Gson getGson()
    {
        if(gson==null)
        {
            GsonBuilder builder = new GsonBuilder();
            gson = builder.create();
        }
        return gson;
    }

}
