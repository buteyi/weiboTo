package cn.buteyi.weiboto.login.presenter.imp;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sina.weibo.sdk.net.WeiboParameters;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cn.buteyi.weiboto.common.AccessTokenKeeper;
import cn.buteyi.weiboto.common.Constants;
import cn.buteyi.weiboto.common.HttpResponse;
import cn.buteyi.weiboto.entities.newEntity.StatusEntity;
import cn.buteyi.weiboto.login.net.BaseNetWork;
import cn.buteyi.weiboto.login.net.BaseUrls;
import cn.buteyi.weiboto.login.net.ParameterKeySet;
import cn.buteyi.weiboto.login.presenter.HomePresenter;
import cn.buteyi.weiboto.login.view.HomeView;


/**
 * Created by john on 2017/1/7.
 */

public class HomePresenterImp implements HomePresenter {
    private String url = BaseUrls.HOME_TIME_LINE;
    private int page = 1;
    WeiboParameters mParameters;
    private List<StatusEntity> mEntityList;
    private HomeView mHomeView;

    public HomePresenterImp(HomeView homeView) {
        mHomeView = homeView;
        mEntityList = new ArrayList<>();
        mParameters = new WeiboParameters(Constants.APP_KEY);
    }

    public void loadData() {
        page = 1;
        loadData(false);
    }

    public void loadMore() {
        page++;
        loadData(true);
    }

    public void requestHomeTimeLine() {
        url = BaseUrls.HOME_TIME_LINE;
        loadData();
    }

    public void requestUserTimeLine() {
        url = BaseUrls.USER_TIME_LINE;
        loadData();
    }

    private void loadData(final boolean loadMore) {
        new BaseNetWork(mHomeView.getActivity(), url) {
            public WeiboParameters onPrepare() {
                mParameters.put(ParameterKeySet.AUTH_ACCESS_TOKEN, AccessTokenKeeper.readAccessToken(mHomeView.getActivity()));
                Log.d("buteyi", "loadDatatoken:"+String.valueOf(AccessTokenKeeper.readAccessToken(mHomeView.getActivity())));
                mParameters.put(ParameterKeySet.PAGE, page);
                mParameters.put(ParameterKeySet.COUNT, 3);
                return mParameters;

            }

            public void onFinish(HttpResponse response, boolean success) {
                if (success) {
                    Log.d("buteyi", "http OK");
                    Type type = new TypeToken<ArrayList<StatusEntity>>() {
                    }.getType();
                    List<StatusEntity> list = new Gson().fromJson(response.response, type);
                    if (!loadMore) {
                        mEntityList.clear();
                    }
                    mEntityList.addAll(list);
                    mHomeView.onSuccess(mEntityList);
                } else {
                    mHomeView.onError(response.message);
                }
            }
        }.get();

    }
}
