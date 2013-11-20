package kobe.angariae.Connections;

import java.util.ArrayList;
import java.util.LinkedList;

import android.os.Parcelable;

import kobe.angariae.exception.AnException;

public interface Connection{
	String getServerAddress();
	String getUserName();
	String getPassword();
	String getLabel();
	String getType();
	void setServerAddress(String sa);
	void setUserName(String un);
	void setPassword(String p);
	void setLabel(String l);
	void connect() throws AnException;
	void disconnect() throws AnException;
	String download(String file) throws AnException;
	void setDownloads(String path);
	ArrayList<String> browse(String path) throws AnException;
}
