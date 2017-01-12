package cn.buteyi.weiboto.login.model;

import cn.buteyi.weiboto.entities.newEntity.UserEntity;

/**
 * Created by john on 2017/1/10.
 */

public interface UserModel {
    interface CompletListner{
        void onSuccsee(UserEntity userEntity);
    }
    void getData();
}
