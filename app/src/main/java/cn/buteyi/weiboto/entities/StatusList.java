package cn.buteyi.weiboto.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by john on 2016/12/20.
 */

public class StatusList implements Parcelable {
    protected StatusList(Parcel in) {
    }

    public static final Creator<StatusList> CREATOR = new Creator<StatusList>() {
        @Override
        public StatusList createFromParcel(Parcel in) {
            return new StatusList(in);
        }

        @Override
        public StatusList[] newArray(int size) {
            return new StatusList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
