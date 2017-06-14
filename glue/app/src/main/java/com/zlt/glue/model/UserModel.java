package com.zlt.glue.model;

import com.zlt.framework.bean.IBean;
import com.zlt.framework.core.CoreManager;
import com.zlt.framework.core.featrues.IHttpCallback;
import com.zlt.framework.model.IModel;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/22.
 */

public class UserModel implements IModel,IHttpCallback {

    public void getUser()
    {
        HashMap<String,Object> map = new HashMap<>();
        map.put("id","12");
        map.put("name","北京市");
        CoreManager.getHttp().post("http://123.206.61.198/index.php?r=member/homepage/city_id",map,"er",this);
    }

    @Override
    public void success(String result, String tag) {

    }

    @Override
    public void failed(String msg, String tag, Exception ex) {

    }

    @Override
    public <T extends IBean> T getBean() {
        return null;
    }

    @Override
    public String getJson() {
        return null;
    }
}
