package cn.buteyi.weiboto.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

/**
 * Created by john on 2017/1/1.
 */

public class AccessTokenKeeper {
    private static final String PREFERENCES_NAME = "com.weibo.sdk.android";

    private static final String KEY_UID = "uid";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_EXPIRES_IN = "exprires_in";
    private static final String KEY_REFRESH_TOKEN = "refresh_token";

    //将 token 对象保存到 SharePreferences 中

    public static void writeAccessToken(Context context, Oauth2AccessToken token) {
        if (null == context || null == token){
            return;
        }

        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_UID, token.getUid());
        editor.putString(KEY_ACCESS_TOKEN, token.getToken());
        editor.putString(KEY_REFRESH_TOKEN, token.getRefreshToken());
        editor.putLong(KEY_EXPIRES_IN, token.getExpiresTime());
        editor.commit();

    }

    //从 SharePreferences 中读取 token 信息
    public static Oauth2AccessToken readAccessToken(Context context) {
        if (null == context){
            return null;
        }

        Oauth2AccessToken token = new Oauth2AccessToken();
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        token.setUid(pref.getString(KEY_UID,""));
        token.setToken(pref.getString(KEY_ACCESS_TOKEN,""));
        token.setRefreshToken(pref.getString(KEY_REFRESH_TOKEN, ""));
        token.setExpiresTime(pref.getLong(KEY_EXPIRES_IN, 0));

        return token;
    }

    //清空SharePreferences 中的 token 信息
    public static void clear(Context context) {
        if (null == context){
            return;
        }
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

}