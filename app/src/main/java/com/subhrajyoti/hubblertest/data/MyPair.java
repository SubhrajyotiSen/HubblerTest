package com.subhrajyoti.hubblertest.data;

import android.os.Parcel;
import android.os.Parcelable;

// a temporary class to hold the 2 values to display in recyclerView
public class MyPair implements Parcelable{

    private String string1;
    private String string2;

    public MyPair(String string1, String string2) {
        this.string1 = string1;
        this.string2 = string2;
    }

    private MyPair(Parcel in) {
        string1 = in.readString();
        string2 = in.readString();
    }

    public static final Creator<MyPair> CREATOR = new Creator<MyPair>() {
        @Override
        public MyPair createFromParcel(Parcel in) {
            return new MyPair(in);
        }

        @Override
        public MyPair[] newArray(int size) {
            return new MyPair[size];
        }
    };

    public String getString1() {
        return string1;
    }

    public String getString2() {
        return string2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(string1);
        dest.writeString(string2);

    }
}
