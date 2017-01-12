package cn.buteyi.weiboto.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.legacy.StatusesAPI;

import cn.buteyi.weiboto.R;
import cn.buteyi.weiboto.common.AccessTokenKeeper;
import cn.buteyi.weiboto.common.Constants;
import cn.buteyi.weiboto.utils.ToastUtil;


public class PostActivity extends AppCompatActivity {
    private StatusesAPI postAPI ;
    private String content;
    private TextView mTextView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_repost);
        mTextView = (TextView) findViewById(R.id.etContent);
        postAPI = new StatusesAPI(this, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(this));
        mButton = (Button) findViewById(R.id.send_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPost();
            }
        });
    }


    private void doPost(){
        if (!mTextView.getText().toString().isEmpty()) {
            postWeibo();
            finish();
            ToastUtil.showShort(this, "发布成功");
        }

    }

    private void postWeibo(){
        content = mTextView.getText().toString();
        postAPI.update(content, "0.0", "0.0", new RequestListener() {
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
 * 发布一条新微博(连续两次发布的微博不可以重复)。
 *
 * @param content  要发布的微博文本内容，内容不超过140个汉字
 * @param lat      纬度，有效范围：-90.0到+90.0，+表示北纬，默认为0.0
 * @param lon      经度，有效范围：-180.0到+180.0，+表示东经，默认为0.0
 * @param listener 异步请求回调接口
 */