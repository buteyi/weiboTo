package cn.buteyi.weiboto.login.net;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.AsyncWeiboRunner;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;

import cn.buteyi.weiboto.common.HttpResponse;

/**
 * Created by john on 2017/1/7.
 */

public abstract class BaseNetWork {
    private AsyncWeiboRunner mAsyncWeiboRunner;
    private String url;

    public BaseNetWork(Context context, String url) {
        mAsyncWeiboRunner = new AsyncWeiboRunner(context);
        this.url = url;
        Log.d("buteyi",url);
    }

    private RequestListener mRequestListener = new RequestListener() {
        public void onComplete(String s) {
            Log.d("buteyi",s);
            boolean success = false;
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
                    success = true;

                } else if (object.has("users")) {
                    response.response = object.get("users").toString();
                    success = true;


                } else if (object.has("comments")) {
                    response.response = object.get("comments").toString();
                    success = true;

                } else {
                    response.response = s;
                    success = true;
                }
            }
            onFinish(response, success);
        }

        public void onWeiboException(WeiboException e) {
            HttpResponse response = new HttpResponse();
            response.message = e.getMessage();
            onFinish(response, false);
            Log.d("buteyi","false");

        }
    };

    public void get() {
        mAsyncWeiboRunner.requestAsync(url, onPrepare(), "GET", mRequestListener);
    }

    public void post() {
        mAsyncWeiboRunner.requestAsync(url, onPrepare(), "POST", mRequestListener);
    }

    public void delete() {
        mAsyncWeiboRunner.requestAsync(url, onPrepare(), "DELETE", mRequestListener);

    }

    public abstract WeiboParameters onPrepare();

    public abstract void onFinish(HttpResponse response, boolean success);
}