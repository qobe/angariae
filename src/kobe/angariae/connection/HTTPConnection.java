package kobe.angariae.connection;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

import kobe.angariae.Track;
import kobe.angariae.exception.AnException;

import android.os.Environment;
import android.os.Parcel;

public class HTTPConnection implements Connection{
	private String serverAddress, username, password, label;
	private String type = "HTTP/S";
	
	public HTTPConnection(String l, String s, String u, String p){
		this.label = l;
		this.serverAddress = s;
		this.username = u;
		this.password = p;
	}
	
	public HTTPConnection(){}
	
	@Override
	public void connect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String download(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setDownloads() {
		File f = new File(Environment.getExternalStorageDirectory()+File.separator+label+File.separator);
		f.mkdirs();
	}

	@Override
	public String getServerAddress() {
		return serverAddress;
	}

	@Override
	public String getUserName() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public void setServerAddress(String sa) {
		this.serverAddress = sa;
	}

	@Override
	public void setUserName(String un) {
		this.username = un;
	}

	@Override
	public void setPassword(String p) {
		this.password = p;
	}

	@Override
	public void setLabel(String l) {
		this.label = l;
	}

	@Override
	public String getType() {
		return this.type ;
	}

	@Override
	public ArrayList<Track> browse(String path) throws AnException {
		// TODO Auto-generated method stub
		return null;
	}

}
