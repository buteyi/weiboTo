package cn.buteyi.weiboto.entities.newEntity;

import java.io.Serializable;
import java.util.List;

import cn.buteyi.weiboto.entities.User;

/**
 * Created by john on 2017/1/7.
 */

public class StatusEntity implements Serializable {
    public String created_at;
    public long id;
    public String mid;
    public String idstr;
    public String text;
    public int source_allowclick;
    public int source_type;
    public String source;
    public boolean favorited;
    public boolean truncated;
    public String in_reply_to_status_id;
    public String in_reply_to_user_id;
    public String in_reply_to_screen_name;
    public String thumbnail_pic;
    public String bmiddle_pic;
    public String original_pic;
    public Object geo;
    public List<PicUrlsEntity> pic_urls;
    public User user;
    public StatusEntity retweeted_status;
    public  int  reposts_count;
    public  int  comments_count;
    public  int  attitudes_count;

    public int deleted;
}
