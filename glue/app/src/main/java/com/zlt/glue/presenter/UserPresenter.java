package com.zlt.glue.presenter;

import com.zlt.framework.presenter.IPresenter;

/**
 * Created by Administrator on 2017/3/22.
 */

public class UserPresenter implements IPresenter {

    public void refreshUser()
    {
        String json = "{\"id\":\"1\",\"name\":\"asdf\"}";//{"id":"1","name":"asdf"}
//        UserModel model = BaseModel.getInstance(UserModel.class, this);
//        model.getUser();
    }
}
