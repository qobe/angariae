package kobe.angariae.Connections;

import java.util.ArrayList;
import java.util.LinkedList;

import android.os.Parcel;

public class HTTPConnection implements Connection{
	private String serverAddress, username, password, label;
	private String type = "HTTP/S";
	@Override
	public void connect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String download(String file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDownloads(String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<String> browse(String path) {
		// TODO Auto-generated method stub
		return null;
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
}
