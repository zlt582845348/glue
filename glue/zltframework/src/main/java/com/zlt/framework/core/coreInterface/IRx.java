package com.zlt.framework.core.coreInterface;

/**
 * Created by Administrator on 2017/3/20.
 */

public interface IRx extends IBase {

    public IRx publish(Object... objs);

    public IRx bind(IRxCallback callback);

    public interface IRxCallback{

        public static final int TYPE_NEXT = 1;
        public static final int TYPE_COMPLETED = 2;
        public static final int TYPE_ERROR = 3;

        public void next(int type, Object obj);

    }
}
