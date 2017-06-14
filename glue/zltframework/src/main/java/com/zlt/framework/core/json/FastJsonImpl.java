package com.zlt.framework.core.json;

import com.alibaba.fastjson.JSON;
import com.zlt.framework.core.featrues.IJson;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/3/21.
 */

public class FastJsonImpl implements IJson {
    @Override
    public String getJson(Object object) {
        return JSON.toJSONString(object);
    }

    @Override
    public <T> T getModel(String json, Class<T> cls) {
        return JSON.parseObject(json,cls);
    }

    @Override
    public <T> T getModel(String json, Type type) {
        return JSON.parseObject(json,type);
    }
}
