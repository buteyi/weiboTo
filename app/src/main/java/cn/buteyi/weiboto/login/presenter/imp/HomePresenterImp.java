package cn.buteyi.weiboto.login.presenter.imp;

import android.util.Log;
import com.sina.weibo.sdk.net.WeiboParameters;
import java.util.ArrayList;
import java.util.List;
import cn.buteyi.weiboto.common.Constants;
import cn.buteyi.weiboto.entities.newEntity.StatusEntity;
import cn.buteyi.weiboto.login.model.StatusListModel;
import cn.buteyi.weiboto.login.model.imp.StatusListModelImp;
import cn.buteyi.weiboto.login.net.BaseUrls;
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
    private StatusListModel statusListModel;


    public HomePresenterImp(HomeView homeView) {
        mHomeView = homeView;
        mEntityList = new ArrayList<>();
        mParameters = new WeiboParameters(Constants.APP_KEY);
        statusListModel = new StatusListModelImp(mHomeView.getActivity(),url,completeListener);

    }


    public void loadMore() {
        page++;
        loadData();
    }

    public void requestHomeTimeLine() {
        url = BaseUrls.HOME_TIME_LINE;
        loadData();
    }

    public void requestUserTimeLine() {
        url = BaseUrls.USER_TIME_LINE;
        loadData();
    }

    public void loadData() {

        statusListModel.setData();

    }

    public StatusListModel.CompleteListener completeListener = new StatusListModel.CompleteListener() {
        @Override
        public void onSuccess(List<StatusEntity> list) {
            mHomeView.onSuccess(list);
          //  Log.d("buteyi","zai homePresenterli");
        }
    };
}
