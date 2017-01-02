package cn.buteyi.weiboto.unlogin.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.buteyi.weiboto.R;

/**
 * Created by john on 2017/1/1.
 */

public class HomeFragment extends Fragment {
    private View mView;

    public HomeFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.unlogin_homefragment_layout, container, false);

        return mView;
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
