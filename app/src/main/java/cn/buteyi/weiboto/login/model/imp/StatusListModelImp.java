package cn.buteyi.weiboto.login.model.imp;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import cn.buteyi.weiboto.api.GroupAPI;
import cn.buteyi.weiboto.api.StatusesAPI;
import cn.buteyi.weiboto.common.AccessTokenKeeper;
import cn.buteyi.weiboto.common.Constants;
import cn.buteyi.weiboto.common.NewFeature;
import cn.buteyi.weiboto.entities.Status;
import cn.buteyi.weiboto.entities.StatusList;
import cn.buteyi.weiboto.login.model.StatusListModel;
import cn.buteyi.weiboto.utils.ToastUtil;

/**
 * Created by john on 2017/1/7.
 */

public class StatusListModelImp implements StatusListModel {

    /**
     * 全局刷新的间隔时间
     */
    private static int REFRESH_FRIENDS_TIMELINE_TASK = 15 * 60 * 1000;
    private ArrayList<Status> mStatusList = new ArrayList<>();
    private Context mContext;
    private OnDataFinishedListener mOnDataFinishedUIListener;
    private OnRequestListener mOnDestroyWeiBoUIListener;
    private Timer mTimer;
    private TimerTask mTimerTask;
    /**
     * 当前的分组位置
     */
    private long mCurrentGroup = Constants.GROUP_TYPE_ALL;
    /**
     * 是否全局刷新
     */
    private boolean mRefrshAll = true;


    /**
     * 获取当前登录用户及其所关注用户的最新微博。
     *
     * @param context
     * @param onDataFinishedListener
     */
    @Override
    public void friendsTimeline(Context context, OnDataFinishedListener onDataFinishedListener) {
        StatusesAPI mStatusesAPI = new StatusesAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        setRefrshFriendsTimelineTask();
        mContext = context;
        mOnDataFinishedUIListener = onDataFinishedListener;
        long sinceId = checkout(Constants.GROUP_TYPE_ALL);
        mStatusesAPI.homeTimeline(sinceId, 0, NewFeature.GET_WEIBO_NUMS, 1, false, 0, false, pullToRefreshListener);
    }


    /**
     * 获取双向关注用户的最新微博。
     *
     * @param context
     * @param onDataFinishedListener
     */
    @Override
    public void bilateralTimeline(Context context, OnDataFinishedListener onDataFinishedListener) {
        StatusesAPI mStatusesAPI = new StatusesAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        setRefrshFriendsTimelineTask();
        mContext = context;
        mOnDataFinishedUIListener = onDataFinishedListener;
        long sinceId = checkout(Constants.GROUP_TYPE_FRIENDS_CIRCLE);
        mStatusesAPI.bilateralTimeline(sinceId, 0, NewFeature.GET_WEIBO_NUMS, 1, false, StatusesAPI.FEATURE_ORIGINAL, false, pullToRefreshListener);
    }

    @Override
    public void timeline(long newGroupId, Context context, OnDataFinishedListener onDataFinishedListener) {
        GroupAPI groupAPI = new GroupAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        setRefrshFriendsTimelineTask();
        mContext = context;
        mOnDataFinishedUIListener = onDataFinishedListener;
        long sinceId = checkout(newGroupId);
        groupAPI.timeline(newGroupId, sinceId, 0, NewFeature.GET_WEIBO_NUMS, 1, false, GroupAPI.FEATURE_ALL, pullToRefreshListener);
    }


    /**
     * 删除一条微博
     *
     * @param id
     * @param context
     * @param onRequestListener
     */
    @Override
    public void weibo_destroy(long id, Context context, OnRequestListener onRequestListener) {
        StatusesAPI mStatusesAPI = new StatusesAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        mContext = context;
        mOnDestroyWeiBoUIListener = onRequestListener;
        mStatusesAPI.destroy(id, destroyRequestListener);
    }


    /**
     * 获取指定分组的下一页微博
     *
     * @param groundId
     * @param context
     * @param onDataFinishedListener
     */
    @Override
    public void timelineNextPage(long groundId, Context context, OnDataFinishedListener onDataFinishedListener) {
        GroupAPI groupAPI = new GroupAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        mContext = context;
        mOnDataFinishedUIListener = onDataFinishedListener;
        setRefrshFriendsTimelineTask();
        String maxId = mStatusList.get(mStatusList.size() - 1).id;
        groupAPI.timeline(groundId, 0, Long.valueOf(maxId), NewFeature.GET_WEIBO_NUMS, 1, false, GroupAPI.FEATURE_ALL, nextPageListener);
    }

    /**
     * 获取我关注的人的下一页微博
     *
     * @param context
     * @param onDataFinishedListener
     */
    @Override
    public void friendsTimelineNextPage(Context context, OnDataFinishedListener onDataFinishedListener) {
        setRefrshFriendsTimelineTask();
        mContext = context;
        mOnDataFinishedUIListener = onDataFinishedListener;
        StatusesAPI mStatusesAPI = new StatusesAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        String maxId = mStatusList.get(mStatusList.size() - 1).id;
        mStatusesAPI.homeTimeline(0, Long.valueOf(maxId), NewFeature.LOADMORE_WEIBO_ITEM, 1, false, 0, false, nextPageListener);
    }


    /**
     * 获取相互关注的人的下一页微博
     *
     * @param context
     * @param onDataFinishedListener
     */
    @Override
    public void bilateralTimelineNextPage(Context context, OnDataFinishedListener onDataFinishedListener) {
        setRefrshFriendsTimelineTask();
        mContext = context;
        mOnDataFinishedUIListener = onDataFinishedListener;
        StatusesAPI mStatusesAPI = new StatusesAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        String maxId = mStatusList.get(mStatusList.size() - 1).id;
        mStatusesAPI.bilateralTimeline(0, Long.valueOf(maxId), NewFeature.LOADMORE_WEIBO_ITEM, 1, false, StatusesAPI.FEATURE_ORIGINAL, false, nextPageListener);
    }


    @Override
    public void setRefrshFriendsTimelineTask() {
        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    mRefrshAll = true;
                }
            };
            mTimer = new Timer();
            mTimer.schedule(mTimerTask, 0, REFRESH_FRIENDS_TIMELINE_TASK);
        }
    }

    @Override
    public void cancelTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }

    /**
     * 用于更新sinceId和maxId的值
     *
     * @param newGroupId
     * @return
     */
    private long checkout(long newGroupId) {
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


    private RequestListener pullToRefreshListener = new RequestListener() {
        @Override
        public void onComplete(String response) {
            StatusList statusList = StatusList.parse(response);
            ArrayList<Status> temp = statusList.statuses;
            if (temp != null && temp.size() > 0) {
                //请求回来的数据的maxid与列表中的第一条的id相同，说明是局部刷新，否则是全局刷新
                ToastUtil.showShort(mContext, temp.size()+ "条新微博");

                //如果是全局刷新,需要清空列表中的全部微博
                if (mStatusList.size() == 0 || !String.valueOf(statusList.max_id).equals(mStatusList.get(0).id)) {
                    mStatusList.clear();
                    mStatusList = temp;
                } else {
                    //如果是局部刷新
                    mStatusList.addAll(0, temp);
                    //更新对象并且序列化到本地
                    statusList.statuses = mStatusList;
                }
                mOnDataFinishedUIListener.onDataFinish(mStatusList);
                mRefrshAll = false;
            } else {
                if (mRefrshAll == false) {//局部刷新，get不到数据
                    ToastUtil.showShort(mContext, "0条新微博");
                    mOnDataFinishedUIListener.noMoreData();
                } else {//全局刷新，get不到数据
                    mOnDataFinishedUIListener.noDataInFirstLoad("你还没有为此组增加成员");
                }
            }
            mRefrshAll = false;
        }

        @Override
        public void onWeiboException(WeiboException e) {
            ToastUtil.showShort(mContext, e.getMessage());
            mOnDataFinishedUIListener.onError(e.getMessage());
        }
    };


    private RequestListener nextPageListener = new RequestListener() {
        @Override
        public void onComplete(String response) {
            if (!TextUtils.isEmpty(response)) {
                ArrayList<Status> temp = (ArrayList<Status>) StatusList.parse(response).statuses;
                if (temp.size() == 0 || (temp != null && temp.size() == 1 && temp.get(0).id.equals(mStatusList.get(mStatusList.size() - 1).id))) {
                    mOnDataFinishedUIListener.noMoreData();
                } else if (temp.size() > 1) {
                    temp.remove(0);
                    mStatusList.addAll(temp);
                    mOnDataFinishedUIListener.onDataFinish(mStatusList);
                }
            } else {
                mOnDataFinishedUIListener.noMoreData();
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            ToastUtil.showShort(mContext, e.getMessage());
            mOnDataFinishedUIListener.onError(e.getMessage());
        }
    };


    private RequestListener destroyRequestListener = new RequestListener() {
        @Override
        public void onComplete(String s) {
            ToastUtil.showShort(mContext, "微博删除成功");
            NewFeature.refresh_profileLayout = true;
            mOnDestroyWeiBoUIListener.onSuccess();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            ToastUtil.showShort(mContext, "微博删除失败");
            ToastUtil.showShort(mContext, e.toString());
            mOnDestroyWeiBoUIListener.onError(e.toString());
        }
    };
}
