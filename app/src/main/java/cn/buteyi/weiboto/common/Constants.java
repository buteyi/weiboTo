package cn.buteyi.weiboto.common;

import android.text.TextUtils;

/**
 * Created by john on 2017/1/1.
 */

public interface Constants {

    public static final String APP_KEY = "3508781971";
    public static final String REDIRECT_URL = "http://oauth.weico.cc";
    public static final String SCOPE = "email,direct_messages_read,direct_messages_write,friendships_groups_read,friendships_groups_write,statuses_to_me_read,follow_app_official_microblog,invitation_write";

    public static final String AppSecret = "531f86684d1cf0dba64a22939bf5d1a4";
    public static final String PackageName = "cn.buteyi.myapp";


    public static final String authurl = "https://open.weibo.cn/oauth2/authorize" + "?" + "client_id=" + Constants.APP_KEY
            + "&response_type=token&redirect_uri=" + Constants.REDIRECT_URL
            + "&key_hash=" + Constants.AppSecret + (TextUtils.isEmpty(Constants.PackageName) ? "" : "&packagename=" + Constants.PackageName)
            + "&display=mobile" + "&scope=" + Constants.SCOPE;

}
