package cn.buteyi.weiboto.entities.newEntity;

/**
 * Created by john on 2017/1/9.
 */

public class CommentEntity {
    public String created_at;
    public long id;
    public String text;
    public String source;
    public UserEntity user;
    public String mid;
    public String idStr;
    public CommentEntity reply_comment;
}
