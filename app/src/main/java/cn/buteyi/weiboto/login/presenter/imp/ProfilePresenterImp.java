package cn.buteyi.weiboto.login.presenter.imp;



import android.content.Context;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.legacy.AccountAPI;

import cn.buteyi.weiboto.common.AccessTokenKeeper;
import cn.buteyi.weiboto.common.Constants;

import cn.buteyi.weiboto.entities.newEntity.UserEntity;
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
    UserEntity userEntity;

    public ProfilePresenterImp(ProfileView profileView){
        this.profileView = profileView;
        context = profileView.getActivity();
        userEntity = new UserEntity();


    }

    @Override
    public void loadData() {
        usersAPI = new UsersAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        String s = AccessTokenKeeper.readAccessToken(context).getUid();
        long uid = Long.parseLong(s);
        usersAPI.show(uid, new RequestListener() {
            @Override
            public void onComplete(String s) {
                userEntity = UserEntity.fromJson(s);
             //   Log.d("buteyi","用户名字:"+userEntity.screen_name);
                profileView.updataView(userEntity);

            }

            @Override
            public void onWeiboException(WeiboException e) {

            }
        });


    }



}
