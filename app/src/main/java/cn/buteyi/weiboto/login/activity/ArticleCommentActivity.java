package cn.buteyi.weiboto.login.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;

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
import cn.buteyi.weiboto.login.net.BaseUrls;
import cn.buteyi.weiboto.login.view.ArticleCommentView;
import cn.buteyi.weiboto.login.view.PullToRefreshRecyclerView;

/**
 * Created by john on 2017/1/9.
 */

public class ArticleCommentActivity extends BaseActivity implements ArticleCommentView{
    private StatusEntity mStatusEntity;
    private PullToRefreshRecyclerView rlv;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArticleCommentAdapter mAdapter;
    private List<CommentEntity> mDataSet;
    private Context context;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().setTitle(R.string.title_weibo_detail);
        mStatusEntity = (StatusEntity) getIntent().getSerializableExtra(StatusEntity.class.getSimpleName());
        mDataSet = new ArrayList<>();
        context = this;
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
//        new BaseNetWork(this, BaseUrls.COMMENT_SHOW) {
//            public WeiboParameters onPrepare() {
//                WeiboParameters parameters = new WeiboParameters(CWConstant.APP_KEY);
//                parameters.put(ParameterKeySet.ID, mStatusEntity.id);
//                parameters.put(ParameterKeySet.PAGE, 1);
//                parameters.put(ParameterKeySet.COUNT, 10);
//                parameters.put(ParameterKeySet.AUTH_ACCESS_TOKEN, mSPUtils.getToken().getToken());
//                return parameters;
//            }
//
//            public void onFinish(HttpResponse response, boolean success) {
//                if (success) {
//                    Type type = new TypeToken<ArrayList<CommentEntity>>() {
//                    }.getType();
//                    JsonParser parser = new JsonParser();
//                    JsonElement element = parser.parse(response.response);
//                    if (element.isJsonArray()) {
//                        List<CommentEntity> temp = new Gson().fromJson(element, type);
//                        mDataSet.clear();
//                        ;
//                        mDataSet.addAll(temp);
//                        mAdapter.notifyDataSetChanged();
//                    }
//                }
//            }
//        }.get();
        RequestListener listener = new RequestListener() {
            @Override
            public void onComplete(String s) {

            }

            @Override
            public void onWeiboException(WeiboException e) {

            }
        };
        CommentsAPI commentsAPI = new CommentsAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        //commentsAPI.show(mStatusEntity.id);

    }

    /**
     * 根据微博ID返回某条微博的评论列表。
     *
     * @param id         需要查询的微博ID。
     * @param since_id   若指定此参数，则返回ID比since_id大的评论（即比since_id时间晚的评论），默认为0。
     * @param max_id     若指定此参数，则返回ID小于或等于max_id的评论，默认为0。
     * @param count      单页返回的记录条数，默认为50
     * @param page       返回结果的页码，默认为1。
     * @param authorType 作者筛选类型，0：全部、1：我关注的人、2：陌生人 ,默认为0。可为以下几种 :
     *                   <li>{@link #AUTHOR_FILTER_ALL}
     *                   <li>{@link #AUTHOR_FILTER_ATTENTIONS}
     *                   <li>{@link #AUTHOR_FILTER_STRANGER}
     * @param listener   异步请求回调接口
     */
//    public void show(long id, long since_id, long max_id, int count, int page, int authorType, RequestListener listener) {
//        WeiboParameters params = buildTimeLineParamsBase(since_id, max_id, count, page);
//        params.put("id", id);
//        params.put("filter_by_author", authorType);
//        requestAsync(sAPIList.get(READ_API_SHOW), params, HTTPMETHOD_GET, listener);
//    }
//


    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onSuccess(List<CommentEntity> commentEntity) {

    }
}
