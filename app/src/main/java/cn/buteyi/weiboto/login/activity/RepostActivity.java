package cn.buteyi.weiboto.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.legacy.StatusesAPI;

import cn.buteyi.weiboto.R;
import cn.buteyi.weiboto.api.CommentsAPI;
import cn.buteyi.weiboto.common.AccessTokenKeeper;
import cn.buteyi.weiboto.common.Constants;
import cn.buteyi.weiboto.login.MainActivity;
import cn.buteyi.weiboto.login.net.ParameterKeySet;
import cn.buteyi.weiboto.utils.RichTextUtils;

public class RepostActivity extends BaseActivity {
    private EditText etContent;
    private Button mButton;
    private long id;
    private String content;
    private String action;
    private String type;
    private StatusesAPI post;
    //发送的文本
    private String status;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_repost);
        post = new StatusesAPI(this,Constants.APP_KEY, AccessTokenKeeper.readAccessToken(this));
        id = getIntent().getLongExtra(ParameterKeySet.ID, 0);
        action = getIntent().getAction();
        etContent = (EditText) findViewById(R.id.etContent);
        mButton = (Button) findViewById(R.id.send_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPost();
            }
        });
        if (!TextUtils.isEmpty(content)) {
            etContent.setText(RichTextUtils.getRichText(getApplicationContext(),"//"+ content));
            Log.d("buteyi","来到了评论微博 RepostActivity");

        }
        switch (action) {
            case "COMMENT":
                getToolbar().setTitle("评论微博");
                type = "comment";
                break;
            case "REPOST":
                getToolbar().setTitle("转发微博");
                content = getIntent().getStringExtra(ParameterKeySet.STATUS);
                type = "repost";
                break;
        }
        Log.d("buteyi","来到了评论微博 RepostActivity1");
        initialize();
    }

    public int getLayoutId() {
        return R.layout.ac_repost;
    }

    private void initialize() {

        etContent = (EditText) findViewById(R.id.etContent);
        if (!TextUtils.isEmpty(content)) {
            etContent.setText(RichTextUtils.getRichText(getApplicationContext(),"//"+ content));
            Log.d("buteyi","来到了评论微博 RepostActivity");

        }
    }

    //发送按钮的处理方法
    private void doPost(){
        if (type.equals("comment")){
            commentWeibo();
        }else {
            Repost();
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        //finish();
    }

    //转发微博
    private void Repost(){
        status = etContent.getText().toString();

        post.repost(id, status, 0, new RequestListener() {
            @Override
            public void onComplete(String s) {

            }

            @Override
            public void onWeiboException(WeiboException e) {

            }
        });
    }

    //评论微博
    private void commentWeibo(){
        status = etContent.getText().toString();
        CommentsAPI commentsAPI = new CommentsAPI(this,Constants.APP_KEY,AccessTokenKeeper.readAccessToken(this));
        commentsAPI.create(status, id, true, new RequestListener() {
            @Override
            public void onComplete(String s) {

            }

            @Override
            public void onWeiboException(WeiboException e) {

            }
        });
    }


}
/**
 * 转发一条微博。
 *
 * @param id            要转发的微博ID
 * @param status        添加的转发文本，内容不超过140个汉字，不填则默认为“转发微博”
 * @param commentType   是否在转发的同时发表评论，0：否、1：评论给当前微博、2：评论给原微博、3：都评论，默认为0
 *                      <li> {@link #COMMENTS_NONE}
 *                      <li> {@link #COMMENTS_CUR_STATUSES}
 *                      <li> {@link #COMMENTS_RIGAL_STATUSES}
 *                      <li> {@link #COMMENTS_BOTH}
 * @param listener      异步请求回调接口
 */
/**
 * 对一条微博进行评论。
 *
 * @param comment     评论内容，内容不超过140个汉字。
 * @param id          需要评论的微博ID。
 * @param comment_ori 当评论转发微博时，是否评论给原微博
 * @param listener    异步请求回调接口
 */