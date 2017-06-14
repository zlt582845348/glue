package com.zlt.framework.reflash;

import android.content.Context;

/**
 * Created by Administrator on 2017/6/6.
 */

public class RefreshViewManager {

    public static IRefreshView getFooter(Context context)
    {
        return new FooterView(context);
    }

}
