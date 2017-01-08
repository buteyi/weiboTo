package cn.buteyi.weiboto.login.view;

import java.util.List;

import cn.buteyi.weiboto.entities.newEntity.StatusEntity;

/**
 * Created by john on 2017/1/7.
 */

public interface HomeView extends BaseView{
    void onSuccess(List<StatusEntity> list);
}
