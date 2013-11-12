package kobe.angariae.Connections;

import java.util.LinkedList;

import kobe.angariae.exception.AnException;

public interface Connection {
	void connect(String serverAddr, String uname, String passwd) throws AnException;
	void disconnect() throws AnException;
	String download(String file) throws AnException;
	void setDownloads(String path);
	LinkedList<String> browse(String path) throws AnException;
}
