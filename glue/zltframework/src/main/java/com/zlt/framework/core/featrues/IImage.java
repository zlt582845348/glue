package com.zlt.framework.core.featrues;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/3/20.
 */

public interface IImage extends IBase {

    public void load(Context context,String path,ImageView view);

    public void load(Context context,int resId,ImageView view);
}
