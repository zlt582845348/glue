package com.zlt.framework.core.log;

import com.orhanobut.logger.Logger;
import com.zlt.framework.core.featrues.ILog;

/**
 * Created by Administrator on 2017/3/22.
 */

public class LoggerImpl implements ILog {

    @Override
    public void d(Object msg) {
        Logger.d(msg);
    }

    @Override
    public void e(String msg) {
        Logger.e(msg);
    }

    @Override
    public void w(String msg) {
        Logger.w(msg);
    }

    @Override
    public void v(String msg) {
        Logger.v(msg);
    }

    @Override
    public void json(String msg) {
        Logger.json(msg);
    }
}
