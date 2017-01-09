package cn.buteyi.weiboto.login.view;

import java.util.List;

import cn.buteyi.weiboto.entities.newEntity.CommentEntity;

/**
 * Created by john on 2017/1/9.
 */

public interface ArticleCommentView extends BaseView {
    void onSuccess(List<CommentEntity>list);
}
