package cn.buteyi.weiboto.login.presenter.imp;



import android.content.Context;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.legacy.AccountAPI;

import cn.buteyi.weiboto.common.AccessTokenKeeper;
import cn.buteyi.weiboto.common.Constants;
import cn.buteyi.weiboto.entities.UserUid;
import cn.buteyi.weiboto.login.presenter.ProfilePresenter;
import cn.buteyi.weiboto.login.view.ProfileView;


/**
 * Created by john on 2017/1/10.
 */

public class ProfilePresenterImp implements ProfilePresenter {
    private ProfileView profileView;
    private AccountAPI accountAPI;
    private UsersAPI usersAPI;
    private Context context;
    private UserUid userUid;
    long uuid;

    public ProfilePresenterImp(ProfileView profileView){
        this.profileView = profileView;
        context = profileView.getActivity();


    }

    @Override
    public void loadData() {
        usersAPI = new UsersAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        String s = AccessTokenKeeper.readAccessToken(context).getUid();
        long uid = Long.parseLong(s);
        usersAPI.show(uid, new RequestListener() {
            @Override
            public void onComplete(String s) {
                Log.d("buteyi","用户信息为:"+s);
                Log.d("buteyi","到get里");
            }

            @Override
            public void onWeiboException(WeiboException e) {

            }
        });


    }



}
