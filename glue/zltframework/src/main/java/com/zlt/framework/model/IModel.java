package com.zlt.framework.model;

import com.zlt.framework.bean.IBean;

/**
 * Created by Administrator on 2017/3/20.
 */

public interface IModel {

    public <T extends IBean> T getBean();

    public String getJson();
}
