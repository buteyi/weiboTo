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

public class ProfileFragment extends Fragment {
    private View mView;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.unlogin_profilefragment_layout, container, false);
        return mView;
    }

}
