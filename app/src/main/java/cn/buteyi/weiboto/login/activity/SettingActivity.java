package cn.buteyi.weiboto.login.activity;

import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.LogoutAPI;

import cn.buteyi.weiboto.R;
import cn.buteyi.weiboto.common.AccessTokenKeeper;
import cn.buteyi.weiboto.common.Constants;
import cn.buteyi.weiboto.unlogin.UnloginActivity;

public class SettingActivity extends AppCompatActivity {
    private Context mContext;
    private RelativeLayout mExitLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.setting_layout);
        mExitLayout = (RelativeLayout) findViewById(R.id.exitLayout);
        setUpListener();

    }

    private void setUpListener(){
        mExitLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("确定要退出此账号?")
                        .setCancelable(false)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LogoutAPI logoutAPI = new LogoutAPI(mContext, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(mContext));
                                logoutAPI.logout(new RequestListener() {
                                    @Override
                                    public void onComplete(String s) {
  //                                      Log.d("buteyi",s);

                                            AccessTokenKeeper.clear(mContext);
                                            Intent intent = new Intent(mContext, UnloginActivity.class);
                                            startActivity(intent);
                                            finish();

                                    }

                                    @Override
                                    public void onWeiboException(WeiboException e) {

                                    }
                                });

                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }
}
