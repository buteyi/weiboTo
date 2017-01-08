package cn.buteyi.weiboto.login.model;

import android.content.Context;

import java.util.ArrayList;

import cn.buteyi.weiboto.entities.Status;
import cn.buteyi.weiboto.entities.User;

/**
 * Created by john on 2017/1/7.
 */

public interface UserModel {

    interface OnUserDetailRequestFinish {
        void onComplete(User user);

        void onError(String error);
    }


    interface OnUserListRequestFinish {
        void noMoreDate();

        void onDataFinish(ArrayList<User> userlist);

        void onError(String error);
    }

    interface OnStatusListFinishedListener {
        void noMoreDate();

        void onDataFinish(ArrayList<Status> statuslist);

        void onError(String error);
    }

    interface OnUserDeleteListener {
        void onSuccess(ArrayList<User> userlist);

        void onEmpty();

        void onError(String error);
    }


    public void show(long uid, Context context, OnUserDetailRequestFinish onUserDetailRequestFinish);

    public void show(String screenName, Context context, OnUserDetailRequestFinish onUserDetailRequestFinish);

    public User showUserDetailSync(long uid, Context context);

    public void userTimeline(long uid, int groupId, Context context, OnStatusListFinishedListener onStatusFinishedListener);

    public void userTimeline(String screenName, int groupId, Context context, OnStatusListFinishedListener onStatusFinishedListener);

    public void userPhoto(String screenName, int groupId, Context context, OnStatusListFinishedListener onStatusFinishedListener);

    public void userTimelineNextPage(long uid, int groupId, Context context, OnStatusListFinishedListener onStatusFinishedListener);

    public void userTimelineNextPage(String screenName, int groupId, Context context, OnStatusListFinishedListener onStatusFinishedListener);

    public void userPhotoNextPage(String screenName, int groupId, Context context, OnStatusListFinishedListener onStatusFinishedListener);

    public void followers(long uid, Context context, OnUserListRequestFinish onUserListRequestFinish);

    public void followersNextPage(long uid, Context context, OnUserListRequestFinish onUserListRequestFinish);

    public void friends(long uid, Context context, OnUserListRequestFinish onUserListRequestFinish);

    public void friendsNextPage(long uid, Context context, OnUserListRequestFinish onUserListRequestFinish);

    public void getUserDetailList(Context context, OnUserListRequestFinish onUserListRequestFinish);

    public void deleteUserByUid(long uid, Context context, OnUserDeleteListener onUserDeleteListener);



}
