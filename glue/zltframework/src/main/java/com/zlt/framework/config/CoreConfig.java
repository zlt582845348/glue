package com.zlt.framework.config;

import com.zlt.framework.core.http.OkHttpImpl;
import com.zlt.framework.core.image.PicassoImpl;
import com.zlt.framework.core.json.GsonImpl;
import com.zlt.framework.core.log.LoggerImpl;

/**
 * Created by Administrator on 2017/3/20.
 */

public class CoreConfig {

    public static final String http_impl_class = OkHttpImpl.class.getName();

    public static final String image_impl_class = PicassoImpl.class.getName();

    public static final String json_impl_class = GsonImpl.class.getName();

    public static final String log_impl_class = LoggerImpl.class.getName();

}
