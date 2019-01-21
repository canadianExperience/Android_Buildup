
package io.buildup.myfirstbuildup20190119013118.ds;
import android.graphics.Bitmap;
import android.net.Uri;

import buildup.mvp.model.IdentifiableBean;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class MyFirstBuildupSourceDSItem implements Parcelable, IdentifiableBean {

    @SerializedName("name") public String name;
    @SerializedName("desc") public String desc;
    @SerializedName("picture") public String picture;
    @SerializedName("code") public String code;
    @SerializedName("id") public String id;
    @SerializedName("pictureUri") public transient Uri pictureUri;

    @Override
    public String getIdentifiableId() {
      return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(desc);
        dest.writeString(picture);
        dest.writeString(code);
        dest.writeString(id);
    }

    public static final Creator<MyFirstBuildupSourceDSItem> CREATOR = new Creator<MyFirstBuildupSourceDSItem>() {
        @Override
        public MyFirstBuildupSourceDSItem createFromParcel(Parcel in) {
            MyFirstBuildupSourceDSItem item = new MyFirstBuildupSourceDSItem();

            item.name = in.readString();
            item.desc = in.readString();
            item.picture = in.readString();
            item.code = in.readString();
            item.id = in.readString();
            return item;
        }

        @Override
        public MyFirstBuildupSourceDSItem[] newArray(int size) {
            return new MyFirstBuildupSourceDSItem[size];
        }
    };

}

