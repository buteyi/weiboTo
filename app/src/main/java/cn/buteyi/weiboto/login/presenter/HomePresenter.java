package cn.buteyi.weiboto.login.presenter;

import android.content.Context;

/**
 * Created by john on 2017/1/7.
 */

public interface HomePresenter {
    void loadData();
    void loadMore();
    void requestHomeTimeLine();
    void requestUserTimeLine();
}
