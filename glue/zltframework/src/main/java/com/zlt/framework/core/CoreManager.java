package com.zlt.framework.core;

import com.zlt.framework.config.CoreConfig;
import com.zlt.framework.core.featrues.IHttp;
import com.zlt.framework.core.featrues.IImage;
import com.zlt.framework.core.featrues.IJson;
import com.zlt.framework.core.featrues.ILog;

/**
 * Created by Administrator on 2017/3/21.
 */

public class CoreManager {

    public static IHttp httpInstance;
    private static IImage imageInstance;
    private static IJson jsonInstance;
    private static ILog logInstance;

    public static IHttp getHttp()
    {
        if(httpInstance==null)
        {
            httpInstance = (IHttp)getInstance(CoreConfig.http_impl_class);
        }
        return httpInstance;
    }

    public static IImage getImage()
    {
        if(imageInstance==null)
        {
            imageInstance = (IImage)getInstance(CoreConfig.image_impl_class);
        }
        return imageInstance;
    }

    public static IJson getJson()
    {
        if(jsonInstance==null)
        {
            jsonInstance = (IJson)getInstance(CoreConfig.json_impl_class);
        }
        return jsonInstance;
    }

    public static ILog getLog()
    {
        if(logInstance==null)
        {
            logInstance = (ILog)getInstance(CoreConfig.log_impl_class);
        }
        return logInstance;
    }

    private static Object getInstance (String clsName) {
        Object object = null;
        try {
            object = Class.forName(clsName).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }

}
