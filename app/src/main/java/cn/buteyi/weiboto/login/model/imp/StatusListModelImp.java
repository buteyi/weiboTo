package cn.buteyi.weiboto.login.model.imp;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.buteyi.weiboto.api.GroupAPI;
import cn.buteyi.weiboto.api.StatusesAPI;
import cn.buteyi.weiboto.common.AccessTokenKeeper;
import cn.buteyi.weiboto.common.Constants;
import cn.buteyi.weiboto.common.HttpResponse;
import cn.buteyi.weiboto.common.NewFeature;
import cn.buteyi.weiboto.entities.Status;
import cn.buteyi.weiboto.entities.StatusList;
import cn.buteyi.weiboto.entities.newEntity.StatusEntity;
import cn.buteyi.weiboto.login.model.StatusListModel;
import cn.buteyi.weiboto.utils.ToastUtil;

/**
 * Created by john on 2017/1/7.
 */

public class StatusListModelImp implements StatusListModel {
    private Context mContext;
    private String URL;
    private String appKey;
    private Oauth2AccessToken accessToken;
    private CompleteListener completeListener;
    private List<StatusEntity> mEntityList;


    public StatusListModelImp(Context context, String url, CompleteListener completeListener){
        mContext = context;
        URL = url;
        appKey = Constants.APP_KEY;
        accessToken = AccessTokenKeeper.readAccessToken(mContext);
        this.completeListener = completeListener;
        mEntityList = new ArrayList<>();
    }

    @Override
    public void setData(){
        /**
         * homeTimeline 参数。
         *
         * @param since_id    若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0
         * @param max_id      若指定此参数，则返回ID小于或等于max_id的微博，默认为0
         * @param count       单页返回的记录条数，默认为50
         * @param page        返回结果的页码，默认为1
         * @param base_app    是否只获取当前应用的数据。false为否（所有数据），true为是（仅当前应用），默认为false
         * @param featureType 过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0
         *                    <li> {@link #FEATURE_ALL}
         *                    <li> {@link #FEATURE_ORIGINAL}
         *                    <li> {@link #FEATURE_PICTURE}
         *                    <li> {@link #FEATURE_VIDEO}
         *                    <li> {@link #FEATURE_MUSICE}
         * @param trim_user   返回值中user字段开关，false：返回完整user字段、true：user字段仅返回user_id，默认为false
         * @param listener    异步请求回调接口
         */

        final List<StatusEntity> entityList = new ArrayList<>();
        StatusesAPI statusesAPI = new StatusesAPI(mContext, URL,appKey, accessToken);
        statusesAPI.homeTimeline(0, 0, 50, 1, false, 0, false, new RequestListener() {
            public void onComplete(String s) {
                Log.d("buteyi", s);
                HttpResponse response = new HttpResponse();
                JsonParser parser = new JsonParser();

                JsonElement element = parser.parse(s);
                if (element.isJsonObject()) {
                    JsonObject object = element.getAsJsonObject();
                    if (object.has("error_code")) {
                        response.code = object.get("error_code").getAsInt();
                    }
                    if ((object.has("error"))) {
                        response.message = object.get("error").getAsString();
                    }
                    if (object.has("statuses")) {
                        response.response = object.get("statuses").toString();

                    } else if (object.has("users")) {
                        response.response = object.get("users").toString();

                    } else if (object.has("comments")) {
                        response.response = object.get("comments").toString();

                    } else {
                        response.response = s;
                    }
                }
                //填充微博数据
                Type type = new TypeToken<ArrayList<StatusEntity>>() {
                }.getType();
                List<StatusEntity> list = new Gson().fromJson(response.response, type);

                mEntityList.addAll(list);
                completeListener.onSuccess(mEntityList);
                Log.d("buteyi","在statusListModelImp里");
            }

            @Override
            public void onWeiboException(WeiboException e) {

            }

        });
    }

}
