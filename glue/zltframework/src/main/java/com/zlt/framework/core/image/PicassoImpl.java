package com.zlt.framework.core.image;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.zlt.framework.core.featrues.IImage;

/**
 * Created by Administrator on 2017/3/21.
 */

public class PicassoImpl implements IImage {

    @Override
    public void load(Context context, String path, ImageView view) {
        Picasso.with(context).load(path).into(view);
    }

    @Override
    public void load(Context context, int resId, ImageView view) {
        Picasso.with(context).load(resId).into(view);
    }

}
