package com.yantang.juney.maintain.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wangqing on 2017/11/1.
 */

public class ImageUrlBean implements Parcelable {

    /**
     * result : Imgs\3\1711\1711011459021123374.jpg
     * type : 3
     */

    private String result;
    private String type;

    public ImageUrlBean(String result, String type) {
        this.result = result;
        this.type = type;
    }

    protected ImageUrlBean(Parcel in) {
        result = in.readString();
        type = in.readString();
    }

    public static final Creator<ImageUrlBean> CREATOR = new Creator<ImageUrlBean>() {
        @Override
        public ImageUrlBean createFromParcel(Parcel in) {
            return new ImageUrlBean(in);
        }

        @Override
        public ImageUrlBean[] newArray(int size) {
            return new ImageUrlBean[size];
        }
    };

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(result);
        dest.writeString(type);

    }
}
