package com.zlt.glue.bean;

import com.zlt.framework.bean.IBean;

/**
 * Created by Administrator on 2017/3/22.
 */

public class UserBean implements IBean {

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private int state;
    private String id;


}
