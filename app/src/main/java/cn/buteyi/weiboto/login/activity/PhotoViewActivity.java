package cn.buteyi.weiboto.login.activity;

import android.os.Bundle;

import com.bumptech.glide.Glide;

import cn.buteyi.weiboto.R;
import cn.buteyi.weiboto.entities.newEntity.PicUrlsEntity;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by john on 2017/1/7.
 */

public class PhotoViewActivity extends BaseActivity {
    private PhotoView photoview;
    private PicUrlsEntity mPicUrlsEntity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getToolbar().hide();
        mPicUrlsEntity = (PicUrlsEntity) getIntent().getSerializableExtra(PicUrlsEntity.class.getSimpleName());
        initialize();
    }

    public int getLayoutId() {
        return R.layout.ac_photoview;
    }

    private void initialize() {
        photoview = (PhotoView) findViewById(R.id.photoview);
        Glide.with(this).load(mPicUrlsEntity.original_pic).asBitmap().fitCenter().into(photoview);
    }
}
