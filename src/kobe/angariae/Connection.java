package kobe.angariae;

import java.util.LinkedList;

public interface Connection {
	void connect(String serverAddr, String uname, String passwd);
	void disconnect();
	String download(String file);
	void setDownloads(String path);
	LinkedList<String> browse();
}
