package kobe.angariae.Connections;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;


import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FTPConnection implements Connection {

	/**
	 * private class globals
	 */
	private FTPClient ftpc;
	private String downloadsDir;
	
	public FTPConnection(){
		ftpc = new FTPClient();
		ftpc.setControlKeepAliveTimeout(420);//set timeout to 7 minutes
	}
	
	public void setDownloads(String path){
		downloadsDir = path+"/";
	}
	
	public void connect(String serverAddr, String username, String password){
		try{
			int reply;
			ftpc.connect(serverAddr);
			ftpc.getReplyString();
			reply = ftpc.getReplyCode();
			if(!FTPReply.isPositiveCompletion(reply)){
				ftpc.disconnect();
				//error, server refused connection
			}
			ftpc.user(username);
			//get reply code
			ftpc.pass(password);
			//get, check reply code
		}catch(IOException e){
			//connection fail, or username/password incorrect
			//throw custom exception up to user level
			e.printStackTrace();
		}
	}

	public void disconnect(){
		try {
			ftpc.disconnect();
		} catch (IOException e) {
			//errors disconnecting
			e.printStackTrace();
		}
	}
	

	public LinkedList<String> browse(String path){
		LinkedList<String> dirContents = new LinkedList<String>();
		try {
			if(path == ".."){
				ftpc.changeToParentDirectory();
			}else if (!path.equalsIgnoreCase(".")){
				ftpc.changeWorkingDirectory(path);	
			}
			FTPFile[] files = ftpc.listFiles();
			for(int i=0;i<files.length;i++){
				dirContents.add(files[i].toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dirContents;
	}
	
	public String download(String filename){
		try {
			File fileDestination = new File(downloadsDir+filename);
			if(!fileDestination.exists()){
				FileOutputStream fos = new FileOutputStream(downloadsDir+filename);
				ftpc.retrieveFile(filename, fos);
				fos.close();
			}
		} catch (IOException e) {
			// connection closure
			e.printStackTrace();
		}
		return downloadsDir+filename;
	}
	
	
	
	
	
}
