package kobe.angariae.Connections;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

import kobe.angariae.exception.AnException;


import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FTPConnection implements Connection {

	private FTPClient ftpc;
	private String downloadsDir;
	
	public FTPConnection(){
		ftpc = new FTPClient();
		ftpc.setControlKeepAliveTimeout(420);//set timeout to 7 minutes
	}
	
	public void setDownloads(String path){
		downloadsDir = path+"/";
	}
	
	public void connect(String serverAddr, String username, String password) throws AnException{
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
		}catch(java.net.UnknownHostException e){
			throw new AnException("Error: Unknown host.",e);
		}catch(IOException e){			
			throw new AnException("Error: Failed to connect. Please check credentials",e);
		}
	}

	public void disconnect() throws AnException{
		try {
			ftpc.disconnect();
		} catch (IOException e) {
			throw new AnException("Error: Could not disconnect.",e);
		}
	}
	

	public LinkedList<String> browse(String path) throws AnException{
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
			throw new AnException("Error: Connection is closed.",e);
		}
		return dirContents;
	}
	
	public String download(String filename) throws AnException{
		try {
			File fileDestination = new File(downloadsDir+filename);
			if(!fileDestination.exists()){
				FileOutputStream fos = new FileOutputStream(downloadsDir+filename);
				ftpc.retrieveFile(filename, fos);
				fos.close();
			}
		} catch (IOException e) {
			// connection closure
			throw new AnException("Error: Connection is closed.",e);
		}
		return downloadsDir+filename;
	}
	
	
	
	
	
}
