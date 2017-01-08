package cn.buteyi.weiboto.login.model.imp;

import android.content.Context;
import android.text.TextUtils;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

import java.util.ArrayList;

import cn.buteyi.weiboto.api.FriendshipsAPI;
import cn.buteyi.weiboto.api.StatusesAPI;
import cn.buteyi.weiboto.api.UsersAPI;
import cn.buteyi.weiboto.common.AccessTokenKeeper;
import cn.buteyi.weiboto.common.Constants;
import cn.buteyi.weiboto.common.NewFeature;
import cn.buteyi.weiboto.entities.Status;
import cn.buteyi.weiboto.entities.StatusList;
import cn.buteyi.weiboto.entities.Token;
import cn.buteyi.weiboto.entities.User;
import cn.buteyi.weiboto.entities.UserList;
import cn.buteyi.weiboto.login.model.UserModel;
import cn.buteyi.weiboto.utils.ToastUtil;

/**
 * Created by john on 2017/1/7.
 */

public class UserModelImp implements UserModel {private ArrayList<Status> mStatusList = new ArrayList<>();
    private ArrayList<User> mUsersList = new ArrayList<>();
    private ArrayList<User> mUserArrayList;

    private OnStatusListFinishedListener mOnStatusListFinishedListener;
    private OnUserListRequestFinish mOnUserListRequestFinish;
    private OnUserDetailRequestFinish mOnUserDetailRequestFinish;
    private Context mContext;
    private int mCurrentGroup = Constants.GROUP_MYWEIBO_TYPE_ALL;
    private boolean mRefrshAll;

    private int mFollowersCursor;
    private int mFriendsCursor;
    private int mUserListType;
    private static final int FOLLOWERS_LISTS = 0x1;
    private static final int FRIENDS_LISTS = 0x2;

    /**
     * 根据用户ID获取用户信息。异步方法
     *
     * @param uid
     * @param context
     */
    @Override
    public void show(long uid, final Context context, final OnUserDetailRequestFinish onUserDetailRequestFinish) {
        UsersAPI mUsersAPI = new UsersAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        mContext = context;
        mOnUserDetailRequestFinish = onUserDetailRequestFinish;
        mUsersAPI.show(uid, user_PullToRefresh);
    }

    @Override
    public void show(String screenName, Context context, OnUserDetailRequestFinish onUserDetailRequestFinish) {
        UsersAPI mUsersAPI = new UsersAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        mContext = context;
        mOnUserDetailRequestFinish = onUserDetailRequestFinish;
        mUsersAPI.show(screenName, user_PullToRefresh);
    }

    /**
     * 根据用户ID获取用户信息。同步方法
     *
     * @param uid
     * @param context
     * @return
     */
    @Override
    public User showUserDetailSync(long uid, Context context) {
        UsersAPI mUsersAPI = new UsersAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        mContext = context;
        return User.parse(mUsersAPI.showSync(uid));
    }

    /**
     * 获取某个用户最新发表的微博列表。
     *
     * @param uid
     * @param groupId
     * @param context
     * @param onStatusFinishedListener
     */
    @Override
    public void userTimeline(long uid, int groupId, Context context, OnStatusListFinishedListener onStatusFinishedListener) {
        StatusesAPI mStatusesAPI = new StatusesAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        mContext = context;
        mOnStatusListFinishedListener = onStatusFinishedListener;
        //long sinceId = checkout(groupId);
        mStatusesAPI.userTimeline(uid, 0, 0, NewFeature.GET_WEIBO_NUMS, 1, false, groupId, false, statuslist_PullToRefresh);
    }

    @Override
    public void userTimeline(String screenName, int groupId, Context context, OnStatusListFinishedListener onStatusFinishedListener) {
        StatusesAPI mStatusesAPI = new StatusesAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        mContext = context;
        mOnStatusListFinishedListener = onStatusFinishedListener;
        //long sinceId = checkout(groupId);
        mStatusesAPI.userTimeline(screenName, 0, 0, NewFeature.GET_WEIBO_NUMS, 1, false, groupId, false, statuslist_PullToRefresh);
    }

    @Override
    public void userPhoto(String screenName, int groupId, Context context, OnStatusListFinishedListener onStatusFinishedListener) {
        StatusesAPI mStatusesAPI = new StatusesAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        mContext = context;
        mOnStatusListFinishedListener = onStatusFinishedListener;
        //long sinceId = checkout(groupId);
        mStatusesAPI.userTimeline(screenName, 0, 0, 50, 1, false, StatusesAPI.FEATURE_PICTURE, false, statuslist_PullToRefresh);
    }

    /**
     * 获取用户的粉丝列表(最多返回5000条数据)。
     *
     * @param uid
     * @param context
     * @param onUserListRequestFinish
     */
    @Override
    public void followers(long uid, Context context, OnUserListRequestFinish onUserListRequestFinish) {
        FriendshipsAPI mFriendshipsAPI = new FriendshipsAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        mUserListType = FOLLOWERS_LISTS;
        mContext = context;
        mOnUserListRequestFinish = onUserListRequestFinish;
        mFriendshipsAPI.followers(uid, NewFeature.GET_FOLLOWER_NUM, 0, false, userlist_PullToRefresh);
    }

    /**
     * 获取用户的关注列表。
     *
     * @param uid
     * @param context
     * @param onUserListRequestFinish
     */
    @Override
    public void friends(long uid, Context context, OnUserListRequestFinish onUserListRequestFinish) {
        FriendshipsAPI mFriendshipsAPI = new FriendshipsAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        mUserListType = FRIENDS_LISTS;
        mContext = context;
        mOnUserListRequestFinish = onUserListRequestFinish;
        mFriendshipsAPI.friends(uid, NewFeature.GET_FRIENDS_NUM, 0, false, userlist_PullToRefresh);
    }

    @Override
    public void userTimelineNextPage(long uid, int groupId, Context context, OnStatusListFinishedListener onStatusFinishedListener) {
        StatusesAPI mStatusesAPI = new StatusesAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        mContext = context;
        mOnStatusListFinishedListener = onStatusFinishedListener;
        mStatusesAPI.userTimeline(uid, 0, Long.valueOf(mStatusList.get(mStatusList.size() - 1).id), NewFeature.LOADMORE_WEIBO_ITEM, 1, false, groupId, false, statuslist_NextPage);
    }

    @Override
    public void userTimelineNextPage(String screenName, int groupId, Context context, OnStatusListFinishedListener onStatusFinishedListener) {
        StatusesAPI mStatusesAPI = new StatusesAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        mContext = context;
        mOnStatusListFinishedListener = onStatusFinishedListener;
        mStatusesAPI.userTimeline(screenName, 0, Long.valueOf(mStatusList.get(mStatusList.size() - 1).id), NewFeature.LOADMORE_WEIBO_ITEM, 1, false, groupId, false, statuslist_NextPage);
    }

    @Override
    public void userPhotoNextPage(String screenName, int groupId, Context context, OnStatusListFinishedListener onStatusFinishedListener) {
        StatusesAPI mStatusesAPI = new StatusesAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        mContext = context;
        mOnStatusListFinishedListener = onStatusFinishedListener;
        mStatusesAPI.userTimeline(screenName, 0, Long.valueOf(mStatusList.get(mStatusList.size() - 1).id), 50, 1, false, StatusesAPI.FEATURE_PICTURE, false, statuslist_NextPage);
    }


    @Override
    public void followersNextPage(long uid, Context context, OnUserListRequestFinish onUserListRequestFinish) {
        FriendshipsAPI mFriendshipsAPI = new FriendshipsAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        mContext = context;
        mOnUserListRequestFinish = onUserListRequestFinish;
        mFriendshipsAPI.followers(uid, NewFeature.LOADMORE_FOLLOWER_NUM, mFollowersCursor, false, userlist_NextPage);
    }


    @Override
    public void friendsNextPage(long uid, Context context, OnUserListRequestFinish onUserListRequestFinish) {
        FriendshipsAPI mFriendshipsAPI = new FriendshipsAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        mContext = context;
        mOnUserListRequestFinish = onUserListRequestFinish;
        mFriendshipsAPI.friends(uid, NewFeature.LOADMORE_FRIENDS_NUM, mFriendsCursor, false, userlist_NextPage);
    }

    /**
     * 获取登录用户的详细信息
     *
     * @param context
     * @param onUserListRequestFinish
     */
    @Override
    public void getUserDetailList(final Context context, final OnUserListRequestFinish onUserListRequestFinish) {

        final ArrayList<Token> tokenArrayList = null;
        if (tokenArrayList == null || tokenArrayList.size() == 0) {
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                mUserArrayList = new ArrayList<User>();
                for (Token token : tokenArrayList) {
                    mUserArrayList.add(showUserDetailSync(Long.valueOf(token.uid), context));
                }
                onUserListRequestFinish.onDataFinish(mUserArrayList);
            }
        }).start();
    }

    @Override
    public void deleteUserByUid(long uid, Context context, OnUserDeleteListener onUserDeleteListener) {
        int i = 0;
        for (i = 0; i < mUserArrayList.size(); i++) {
            if (mUserArrayList.get(i).id.equals(String.valueOf(uid))) {
                mUserArrayList.remove(i);
                i--;
                break;
            }
        }
        if (mUserArrayList.size() == 0) {
            onUserDeleteListener.onEmpty();
            return;
        }
        if (i >= mUserArrayList.size()) {
            onUserDeleteListener.onError("没有找到对应的账户");
        } else {
            onUserDeleteListener.onSuccess(mUserArrayList);
        }
    }


    /**
     * 用于更新sinceId和maxId的值
     *
     * @param newGroupId
     * @return
     */
    private long checkout(int newGroupId) {
        long sinceId = 0;
        if (mCurrentGroup != newGroupId) {
            mRefrshAll = true;
        }
        if (mStatusList.size() > 0 && mCurrentGroup == newGroupId && mRefrshAll == false) {
            sinceId = Long.valueOf(mStatusList.get(0).id);
        }
        if (mRefrshAll) {
            sinceId = 0;
        }
        mCurrentGroup = newGroupId;
        return sinceId;
    }

    public RequestListener statuslist_NextPage = new RequestListener() {
        @Override
        public void onComplete(String response) {
            if (!TextUtils.isEmpty(response)) {
                ArrayList<Status> temp = StatusList.parse(response).statuses;
                if (temp.size() == 0 || (temp != null && temp.size() == 1 && temp.get(0).id.equals(mStatusList.get(mStatusList.size() - 1).id))) {
                    mOnStatusListFinishedListener.noMoreDate();
                } else if (temp.size() > 1) {
                    temp.remove(0);
                    mStatusList.addAll(temp);
                    mOnStatusListFinishedListener.onDataFinish(mStatusList);
                }
            } else {
                mOnStatusListFinishedListener.noMoreDate();
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            ToastUtil.showShort(mContext, e.getMessage());
            mOnStatusListFinishedListener.onError(e.getMessage());
        }
    };
    public RequestListener userlist_PullToRefresh = new RequestListener() {
        @Override
        public void onComplete(String response) {
            ArrayList<User> temp = UserList.parse(response).users;
            if (temp != null && temp.size() > 0) {
                if (mUsersList != null) {
                    mUsersList.clear();
                }
                mUsersList = temp;
                mFollowersCursor = Integer.valueOf(UserList.parse(response).next_cursor);
                mFriendsCursor = Integer.valueOf(UserList.parse(response).next_cursor);
                mOnUserListRequestFinish.onDataFinish(mUsersList);
            } else {
                ToastUtil.showShort(mContext, "没有更新的内容了");
                mOnUserListRequestFinish.noMoreDate();
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            ToastUtil.showShort(mContext, e.getMessage());
            mOnUserListRequestFinish.onError(e.getMessage());
        }
    };
    public RequestListener userlist_NextPage = new RequestListener() {
        @Override
        public void onComplete(String response) {
            if (!TextUtils.isEmpty(response)) {
                ArrayList<User> temp = UserList.parse(response).users;
                if (temp.size() == 0 || (temp != null && temp.size() == 1 && temp.get(0).id.equals(mUsersList.get(mUsersList.size() - 1).id))) {
                    mOnUserListRequestFinish.noMoreDate();
                } else if (temp.size() > 1) {
                    mUsersList.addAll(temp);
                    mFollowersCursor = Integer.valueOf(UserList.parse(response).next_cursor);
                    mFriendsCursor = Integer.valueOf(StatusList.parse(response).next_cursor);
                    mOnUserListRequestFinish.onDataFinish(mUsersList);
                }
            } else {
                ToastUtil.showShort(mContext, "内容已经加载完了");
                mOnUserListRequestFinish.noMoreDate();
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            ToastUtil.showShort(mContext, e.getMessage());
            mOnUserListRequestFinish.onError(e.getMessage());
        }
    };

    public RequestListener statuslist_PullToRefresh = new RequestListener() {
        @Override
        public void onComplete(String response) {
            ArrayList<Status> temp = StatusList.parse(response).statuses;
            if (temp != null && temp.size() > 0) {
                if (mStatusList != null) {
                    mStatusList.clear();
                }
                mStatusList = temp;
                mOnStatusListFinishedListener.onDataFinish(mStatusList);
            } else {
                mOnStatusListFinishedListener.noMoreDate();
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            ToastUtil.showShort(mContext, e.getMessage());
            mOnStatusListFinishedListener.onError(e.getMessage());
        }
    };

    public RequestListener user_PullToRefresh = new RequestListener() {
        @Override
        public void onComplete(String response) {
            User user = User.parse(response);
            if (user != null) {
                mOnUserDetailRequestFinish.onComplete(user);
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            ToastUtil.showShort(mContext, e.getMessage());
            mOnUserDetailRequestFinish.onError(e.getMessage());
        }
    };
}