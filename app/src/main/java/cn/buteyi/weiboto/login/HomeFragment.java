package cn.buteyi.weiboto.login;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;

import cn.buteyi.weiboto.R;
import cn.buteyi.weiboto.entities.newEntity.StatusEntity;
import cn.buteyi.weiboto.login.adapters.HomepageListAdapter;
import cn.buteyi.weiboto.login.presenter.HomePresenter;
import cn.buteyi.weiboto.login.presenter.imp.HomePresenterImp;
import cn.buteyi.weiboto.login.view.HomeView;
import cn.buteyi.weiboto.login.view.PullToRefreshRecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements HomeView {
    private PullToRefreshRecyclerView rlv;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration mItemDecoration;
    private HomePresenter mPresenter;
    HomepageListAdapter mListAdapter;
    List<StatusEntity> mList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // EventBus.getDefault().register(this);
        mPresenter = new HomePresenterImp(this);
        mList = new ArrayList<>();
        mListAdapter = new HomepageListAdapter(mList,getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rlv = (PullToRefreshRecyclerView) inflater.inflate(R.layout.v_common_recyclerview, container, false);
        init();
        mPresenter.loadData();
        return rlv;
    }

    private void init() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        rlv.getRefreshableView().setLayoutManager(mLayoutManager);
        mItemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
        rlv.addItemDecoration(mItemDecoration);
        rlv.setAdapter(mListAdapter);
        rlv.setMode(PullToRefreshBase.Mode.BOTH);
        rlv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mPresenter.loadData();
            }

            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mPresenter.loadMore();
            }
        });
        mListAdapter.setOnItemClickListener(new HomepageListAdapter.OnItemClickListener() {
            public void onItemClick(View v, int position) {
//                Intent intent = new Intent(getActivity(), ArticleCommentActivity.class);
//                intent.putExtra(StatusEntity.class.getSimpleName(),mList.get(position));
//                startActivity(intent);

            }
        });
    }


    public void onEventMainThread(Object event) {
        if(event instanceof Integer){
            int id  = (int) event;
//            switch (id){
//                case R.id.action_one:
//                    mPresenter.requestHomeTimeLine();
//                    break;
//                case R.id.action_two:
//                    mPresenter.requestUserTimeLine();
//                    break;
//            }
        }

    }

    public void onDestroy() {
        super.onDestroy();
       // EventBus.getDefault().unregister(this);
    }

    public void onSuccess(List<StatusEntity> list) {
        rlv.onRefreshComplete();
        mList.clear();;
        mList.addAll(list);
        mListAdapter.notifyDataSetChanged();
    }

    public void onError(String error) {
        rlv.onRefreshComplete();
        Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
    }

    /**
     * 静态工厂方法需要一个int型的值来初始化fragment的参数，
     * 然后返回新的fragment到调用者
     */
    public static HomeFragment newInstance(boolean comeFromAccoutActivity) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putBoolean("comeFromAccoutActivity", comeFromAccoutActivity);
        homeFragment.setArguments(args);
        return homeFragment;
    }



}
