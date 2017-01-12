package cn.buteyi.weiboto.login;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.legacy.AccountAPI;

import cn.buteyi.weiboto.R;
import cn.buteyi.weiboto.entities.newEntity.UserEntity;

import cn.buteyi.weiboto.login.activity.SettingActivity;
import cn.buteyi.weiboto.login.presenter.ProfilePresenter;
import cn.buteyi.weiboto.login.presenter.imp.ProfilePresenterImp;
import cn.buteyi.weiboto.login.view.ProfileView;
import cn.buteyi.weiboto.utils.CircleTransform;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment implements ProfileView{
    private View mView;
    private ImageView myimgView;
    private TextView myNameView;
    private TextView myDescribeView;
    private TextView statusesCountView;
    private TextView friendsCountView;
    private TextView followersCountView;
    private TextView settingView;

    private ProfilePresenter profilePresenter;
    private Context mContext;
    private Activity mActivity;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profilePresenter = new ProfilePresenterImp(this);

        profilePresenter.loadData();
        mContext = getContext();
        mActivity = getActivity();

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.login_profilefragment_layout, container, false);
        myimgView = (ImageView) mView.findViewById(R.id.profile_myimg);
        myNameView = (TextView)mView.findViewById(R.id.profile_myname);
        myDescribeView = (TextView) mView.findViewById(R.id.profile_mydescribe);
        statusesCountView = (TextView) mView.findViewById(R.id.profile_statuses_count);
        followersCountView = (TextView) mView.findViewById(R.id.profile_followers_count);
        friendsCountView = (TextView) mView.findViewById(R.id.profile_friends_count);
        settingView = (TextView)mView.findViewById(R.id.setting);
        settingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SettingActivity.class);
                startActivity(intent);
            }
        });

        return mView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void updataView(UserEntity userEntity) {
        if (null != userEntity) {

            Glide.with(mActivity).load(userEntity.profile_image_url).transform(new CircleTransform(mContext)).error(R.mipmap.ic_default_header).into(myimgView);
            myNameView.setText(userEntity.screen_name);
            myDescribeView.setText(userEntity.description);
            statusesCountView.setText(Integer.toString(userEntity.statuses_count));
            followersCountView.setText(Integer.toString(userEntity.followers_count));
            friendsCountView.setText(Integer.toString(userEntity.friends_count));
        }

    }

    @Override
    public void onError(String error) {

    }
}
