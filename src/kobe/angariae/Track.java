package kobe.angariae;

import java.io.File;

import org.apache.commons.net.ftp.FTPFile;

import android.os.Parcel;
import android.os.Parcelable;

public class Track implements Parcelable{
	public static final String klass = "track";
	private boolean isDir;
	private boolean isFile;
	private String path;
	private String title;
	private long size;

	public Track(File f){
		this.isDir = f.isDirectory();
		this.isFile = f.isFile();
		this.path = f.getAbsolutePath();
		this.title = f.getName();
		this.size = f.length();
	}
	
	public Track(FTPFile ff){
		this.isDir = ff.isDirectory();
		this.isFile = ff.isFile();
		this.path = ff.toString();
		this.title = ff.getName();
		this.size = ff.getSize();
	}
	
	private Track(Parcel in){
		this.path = in.readString();
		this.title = in.readString();
		this.size = in.readLong();
	}
	
	public boolean isMusic(){
		boolean result=false;
		String pattern = ".*.(3gp|mp4|m4a|aac|3gp|flac|mp3|wav|mid|xmf|mxmf|rtttl|rtx|ogg|mkv|ota|imy)";
		if(title.toLowerCase().matches(pattern)){
			result = true;
		}
		return result;
	}
	
	public boolean isVideo(){
		boolean result=false;
		String pattern = ".*.(3gp|mp4|ts|webm|mkv)";
		if(title.toLowerCase().matches(pattern)){
			result = true;
		}
		return result;
	}
	
	public boolean isDirectory(){
		return isDir;
	}
	public boolean isFile(){
		return isFile;
	}
	public String getPath(){
		return path;
	}
	public String getTitle(){
		return title;
	}

	//returns file size in bytes
	public long getSize(){
		return size;
	}

//	Parcelable methods
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(this.path);
		out.writeString(this.title);
		out.writeLong(this.size);
	}
	
	public static final Parcelable.Creator<Track> CREATOR = new Parcelable.Creator<Track>() {
		public Track createFromParcel(Parcel in){
			return new Track(in);
		}
		public Track[] newArray(int size){
			return new Track[size];
		}
	};
}
