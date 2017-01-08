package cn.buteyi.weiboto.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import cn.buteyi.weiboto.R;

/**
 * Created by john on 2017/1/7.
 */

public abstract class BaseActivity extends AppCompatActivity{
    private RelativeLayout rlContent;
    private Toolbar toolbar;
 //   private ToolbarX mToolbarX;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_baselayout);
        initialize();
        View v= getLayoutInflater().inflate(getLayoutId(), rlContent, false);
        rlContent.addView(v);
    //    mToolbarX = new ToolbarX(toolbar,this);
    }
    public  abstract  int getLayoutId();

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.anmi_in_right_left,R.anim.anmi_out_right_left);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_in_left_right,R.anim.anit_out_left_right);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        overridePendingTransition(R.anim.anmi_in_right_left, R.anim.anmi_out_right_left);
    }

    private void initialize() {

        rlContent = (RelativeLayout) findViewById(R.id.rlContent);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

    }
    public Toolbar getToolbar(){
        return this.toolbar;
    }

}
