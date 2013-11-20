package kobe.angariae.connection;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelableConnection implements Parcelable{
	private Connection c;
	
	public Connection getConnection(){
		return c;
	}
	
	public ParcelableConnection(Connection c){
		super();
		this.c = c;
	}
	
	private ParcelableConnection(Parcel in){
		String tmp = in.readString(); //extract "Type"
		if(tmp.contains("FTP")){
			c = new FTPConnection();
		}else{
			c = new HTTPConnection();
		}
		c.setLabel(in.readString());
		c.setServerAddress(in.readString());
		c.setUserName(in.readString());
		c.setPassword(in.readString());
	}
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(c.getType());
		dest.writeString(c.getLabel());
		dest.writeString(c.getServerAddress());
		dest.writeString(c.getUserName());
		dest.writeString(c.getPassword());
		
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
