package cn.buteyi.weiboto.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import cn.buteyi.weiboto.R;
import cn.buteyi.weiboto.common.AccessTokenKeeper;
import cn.buteyi.weiboto.login.MainActivity;
import cn.buteyi.weiboto.unlogin.UnloginActivity;


/**
 * Created by john on 2017/1/1.
 */

public class WelcomeActivity extends AppCompatActivity {
    private Intent mStartIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
//        mStartIntent = new Intent(WelcomeActivity.this, MainActivity.class);
//        mStartIntent = new Intent(WelcomeActivity.this, UnloginActivity.class);
        if(AccessTokenKeeper.readAccessToken(this).isSessionValid()){
            Log.d("cuizai","yicunzai");
        }
        if (AccessTokenKeeper.readAccessToken(this).isSessionValid()) {
            mStartIntent = new Intent(WelcomeActivity.this, MainActivity.class);
        } else {
            mStartIntent = new Intent(WelcomeActivity.this, UnloginActivity.class);
        }


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mhandler.sendMessage(Message.obtain());
            }
        },500);
    }

    public Handler mhandler = new Handler() {
        public void handleMessage(Message msg) {
            startActivity(mStartIntent);
            finish();
        }
    };
}