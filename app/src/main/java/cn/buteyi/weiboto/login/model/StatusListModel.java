package cn.buteyi.weiboto.login.model;


import cn.buteyi.weiboto.entities.newEntity.StatusEntity;
import java.util.List;

/**
 * Created by john on 2017/1/7.
 */

public interface StatusListModel {
    interface  CompleteListener{

        void onSuccess(List<StatusEntity> list);
    }

     void setData();


}
