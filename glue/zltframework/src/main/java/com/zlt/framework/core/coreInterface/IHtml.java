package com.zlt.framework.core.coreInterface;

import com.zlt.framework.model.IModel;

/**
 * Created by Administrator on 2017/3/20.
 */

public interface IHtml extends IBase {

    public void getModel();

    public IModel getHtml(String html, Class cls);

}
