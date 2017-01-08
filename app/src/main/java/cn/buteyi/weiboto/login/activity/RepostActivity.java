package cn.buteyi.weiboto.login.activity;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.sina.weibo.sdk.net.WeiboParameters;

import cn.buteyi.weiboto.R;
import cn.buteyi.weiboto.common.Constants;
import cn.buteyi.weiboto.common.HttpResponse;
import cn.buteyi.weiboto.login.net.BaseNetWork;
import cn.buteyi.weiboto.login.net.BaseUrls;
import cn.buteyi.weiboto.login.net.ParameterKeySet;
import cn.buteyi.weiboto.utils.RichTextUtils;
import cn.buteyi.weiboto.utils.SPUtils;

public class RepostActivity extends BaseActivity {private EditText etContent;
    private long id;
    private String content;
    private String action;
    private String url;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getLongExtra(ParameterKeySet.ID, 0);
        action = getIntent().getAction();
        switch (action) {
            case "COMMENT":
                getToolbar().setTitle("评论微博");
                url = BaseUrls.COMMENT_CREATE;
                break;
            case "REPOST":
                getToolbar().setTitle("转发微博");
                content = getIntent().getStringExtra(ParameterKeySet.STATUS);
                url =BaseUrls.STATUS_REPOST;
                break;
        }
        initialize();
    }

    public int getLayoutId() {
        return R.layout.ac_repost;
    }

    private void initialize() {

        etContent = (EditText) findViewById(R.id.etContent);
        if (!TextUtils.isEmpty(content)) {
            etContent.setText(RichTextUtils.getRichText(getApplicationContext(),"//"+ content));

        }
    }

//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_repost, menu);
//        return true;
//    }

//    public boolean onOptionsItemSelected(MenuItem item) {
//        post(etContent.getText().toString());
//        return true;
//    }

//    private void post(final String string) {
//        new BaseNetWork(getApplicationContext(),url) {
//            public WeiboParameters onPrepare() {
//                WeiboParameters weiboParameters = new WeiboParameters(Constants.APP_KEY);
//                weiboParameters.put(ParameterKeySet.AUTH_ACCESS_TOKEN, SPUtils.getInstance(getApplicationContext())
//                        .getToken().getToken());
//                if(action.equals("REPOST")){
//                    weiboParameters.put(ParameterKeySet.STATUS,string);
//
//                }
//                else {
//                    weiboParameters.put(ParameterKeySet.COMMENT,  string);
//
//                }
//                weiboParameters.put(ParameterKeySet.ID, id);
//                return weiboParameters;
//            }
//
//            public void onFinish(HttpResponse response, boolean success) {
//                if (success) {
//                    Log.d("RepostActivity", response.response);
//                    EventBus.getDefault().post("onFinish");
//                    finish();
//                }
//            }
//        }.post();
//    }


}
