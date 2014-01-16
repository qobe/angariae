package kobe.angariae;

import java.io.File;

import org.apache.commons.net.ftp.FTPFile;

public class Track{
	private boolean isDir;
	private boolean isFile;
	private String path;
	private String name;
	private long size;

	public Track(File f){
		this.isDir = f.isDirectory();
		this.isFile = f.isFile();
		this.path = f.getAbsolutePath();
		this.name = f.getName();
		this.size = f.length();
	}
	
	public Track(FTPFile ff){
		this.isDir = ff.isDirectory();
		this.isFile = ff.isFile();
		this.path = ff.toString();
		this.name = ff.getName();
		this.size = ff.getSize();
	}
	
	public boolean isMusic(){
		boolean result=false;
		String pattern = "*.(3gp)|(mp4)|(m4a)|(aac)|(3gp)|(flac)|(mp3)|(wav)|"
				+"(mid)|(xmf)|(mxmf)|(rtttl)|(rtx)|(ogg)|(mkv)|(ota)|(imy)";
		if(name.toLowerCase().matches(pattern)){
			result = true;
		}
		return result;
	}
	
	public boolean isVideo(){
		boolean result=false;
		String pattern = "*.(3gp)|(mp4)|(ts)|(webm)|(mkv)";
		if(name.toLowerCase().matches(pattern)){
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
	public String getName(){
		return name;
	}

	//returns file size in bytes
	public long getSize(){
		return size;
	}
}
