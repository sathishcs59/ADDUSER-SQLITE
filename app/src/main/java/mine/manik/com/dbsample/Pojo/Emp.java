package mine.manik.com.dbsample.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by manikkam on 1/2/18.
 */

public class Emp implements Parcelable {

    int id;
    String name,mobile,dep;

    public Emp() {
    }

    protected Emp(Parcel in) {
        id = in.readInt();
        name = in.readString();
        mobile = in.readString();
        dep = in.readString();
    }

    public static final Creator<Emp> CREATOR = new Creator<Emp>() {
        @Override
        public Emp createFromParcel(Parcel in) {
            return new Emp(in);
        }

        @Override
        public Emp[] newArray(int size) {
            return new Emp[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(mobile);
        dest.writeString(dep);
    }
}
