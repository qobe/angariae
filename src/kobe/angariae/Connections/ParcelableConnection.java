package kobe.angariae.Connections;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelableConnection implements Parcelable{
	private Connection c;
	
	public ParcelableConnection(Connection c){
		super();
		this.c = c;
	}
	
	private ParcelableConnection(Parcel in){
		in.readString(); //needs to be TYPE
	}
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
	}

    public static final Parcelable.Creator<ParcelableConnection> CREATOR =
            new Parcelable.Creator<ParcelableConnection>() {
        public ParcelableConnection createFromParcel(Parcel in) {
            return new ParcelableConnection(in);
        }
 
        public ParcelableConnection[] newArray(int size) {
            return new ParcelableConnection[size];
        }
    };
}
