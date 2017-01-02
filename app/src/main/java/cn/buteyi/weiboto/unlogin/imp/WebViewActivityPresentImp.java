package cn.buteyi.weiboto.unlogin.imp;

import android.content.Context;
import android.util.Log;

import cn.buteyi.weiboto.unlogin.WebViewActivityPresent;
import cn.buteyi.weiboto.unlogin.WebViewActivityView;

/**
 * Created by john on 2017/1/1.
 */

public class WebViewActivityPresentImp implements WebViewActivityPresent {
    private WebViewActivityView mWebViewActivityView;

    public WebViewActivityPresentImp(WebViewActivityView mWebViewActivityView){
        this.mWebViewActivityView = mWebViewActivityView;
    }

    @Override
    public void handleRedirectedUrl(Context context, String url) {
        if (!url.contains("error")) {
            int tokenIndex = url.indexOf("access_token=");
            int expiresIndex = url.indexOf("expires_in=");
            int refresh_token_Index = url.indexOf("refresh_token=");
            int uid_Index = url.indexOf("uid=");

            String token = url.substring(tokenIndex + 13, url.indexOf("&", tokenIndex));
            String expiresIn = url.substring(expiresIndex + 11, url.indexOf("&", expiresIndex));
            String refresh_token = url.substring(refresh_token_Index + 14, url.indexOf("&", refresh_token_Index));
            String uid = new String();
            if (url.contains("scope=")) {
                uid = url.substring(uid_Index + 4, url.indexOf("&", uid_Index));
            } else {
                uid = url.substring(uid_Index + 4);
            }

            Log.d("uid=", uid);

            mWebViewActivityView.startMainActivity();
        }
    }
}
