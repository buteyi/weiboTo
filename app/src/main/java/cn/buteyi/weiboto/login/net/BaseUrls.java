package cn.buteyi.weiboto.login.net;

/**
 * Created by john on 2017/1/7.
 */

public class BaseUrls {
    private static final String PREFIX = "https://api.weibo.com/2/";
    public static final String HOME_TIME_LINE = PREFIX+"statuses/home_timeline.json";
    public static final String USER_TIME_LINE = PREFIX+"statuses/user_timeline.json";
    public static final String STATUS_REPOST = PREFIX+"statuses/repost.json";
    public static final String COMMENT_CREATE = PREFIX+"comments/create.json";
    public static final String COMMENT_SHOW = PREFIX+"comments/show.json";
}
