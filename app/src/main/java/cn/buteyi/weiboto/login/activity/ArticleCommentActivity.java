package cn.buteyi.weiboto.login.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cn.buteyi.weiboto.R;
import cn.buteyi.weiboto.api.CommentsAPI;
import cn.buteyi.weiboto.common.AccessTokenKeeper;
import cn.buteyi.weiboto.common.Constants;
import cn.buteyi.weiboto.entities.newEntity.CommentEntity;
import cn.buteyi.weiboto.entities.newEntity.StatusEntity;
import cn.buteyi.weiboto.login.adapters.ArticleCommentAdapter;
import cn.buteyi.weiboto.login.view.PullToRefreshRecyclerView;

public class ArticleCommentActivity extends BaseActivity  {
    private StatusEntity mStatusEntity;
    private PullToRefreshRecyclerView rlv;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArticleCommentAdapter mAdapter;
    private List<CommentEntity> mDataSet;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().setTitle(R.string.title_weibo_detail);
        setContentView(R.layout.v_common_recyclerview);
        mStatusEntity = (StatusEntity) getIntent().getSerializableExtra(StatusEntity.class.getSimpleName());
        mDataSet = new ArrayList<>();
        initialize();
    }

    public int getLayoutId() {
        return R.layout.v_common_recyclerview;
    }

    private void initialize() {

        rlv = (PullToRefreshRecyclerView) findViewById(R.id.rlv);
        mLayoutManager = new LinearLayoutManager(this);
        rlv.setLayoutManager(mLayoutManager);
        mAdapter = new ArticleCommentAdapter(this, mStatusEntity, mDataSet);
        rlv.setAdapter(mAdapter);
        CommentsAPI commentsAPI = new CommentsAPI(this, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(this));
        commentsAPI.show(mStatusEntity.id, 0, 0, 50, 1, 0, new RequestListener() {
            String json = new String();
            Type type = new TypeToken<ArrayList<CommentEntity>>() {
            }.getType();
            @Override
            public void onComplete(String s) {

                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(s);
                if (element.isJsonObject()) {
                    JsonObject object = element.getAsJsonObject();
                    if (object.has("comments")) {
                        json = object.get("comments").toString();
                    }
                }
                element = parser.parse(json);
                if (element.isJsonArray()) {

                    List<CommentEntity> temp = new Gson().fromJson(element, type);
                    mDataSet.clear();
                    mDataSet.addAll(temp);
                    mAdapter.notifyDataSetChanged();
                    Log.d("buteyi","来到了ArticleCommentActivity里");
                }

            }

            @Override
            public void onWeiboException(WeiboException e) {

            }

        });
    }

}
