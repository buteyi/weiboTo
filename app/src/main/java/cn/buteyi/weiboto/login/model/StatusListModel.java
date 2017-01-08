package cn.buteyi.weiboto.login.model;

import android.content.Context;
import cn.buteyi.weiboto.entities.Status;
import cn.buteyi.weiboto.entities.StatusList;

import java.util.ArrayList;

/**
 * Created by john on 2017/1/7.
 */

public interface StatusListModel {

    interface OnDataFinishedListener {
        void noMoreData();

        void noDataInFirstLoad(String error);

        void onDataFinish(ArrayList<Status> statuslist);

        void onError(String error);
    }

    interface OnRequestListener {
        void onSuccess();

        void onError(String error);
    }

    public void timeline(long groundId, Context context, OnDataFinishedListener onDataFinishedListener);

    public void friendsTimeline(Context context, OnDataFinishedListener onDataFinishedListener);

    public void bilateralTimeline(Context context, OnDataFinishedListener onDataFinishedListener);

    public void weibo_destroy(long id, Context context, OnRequestListener onRequestListener);

    public void friendsTimelineNextPage(Context context, OnDataFinishedListener onDataFinishedListener);

    public void bilateralTimelineNextPage(Context context, OnDataFinishedListener onDataFinishedListener);

    public void timelineNextPage(long groundId, Context context, OnDataFinishedListener onDataFinishedListener);

    public void setRefrshFriendsTimelineTask();

    public void cancelTimer();


}
