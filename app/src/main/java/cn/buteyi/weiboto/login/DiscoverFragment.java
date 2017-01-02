package cn.buteyi.weiboto.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import cn.buteyi.weiboto.R;

/**
 * Created by john on 2017/1/2.
 */

public class DiscoverFragment extends Fragment {
    private View mView;
  //  private RelativeLayout mPulicWeibo;

    public DiscoverFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        mView = inflater.inflate(R.layout.login_discoverfragment_layout, container, false);
        return mView;
    }
}
