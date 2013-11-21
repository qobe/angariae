package kobe.angariae.connection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import kobe.angariae.exception.AnException;


import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import android.os.Environment;

public class FTPConnection implements Connection {

	private FTPClient ftpc;
	private String downloadsDir;
	private String serverAddress, username, password, label;
	private String type = "FTP";
	
	public FTPConnection(){
		ftpc = new FTPClient();
		ftpc.setControlKeepAliveTimeout(420);//set timeout to 7 minutes
	}
	
	public FTPConnection(String l, String s, String u, String p){
		this.label = l;
		this.serverAddress = s;
		this.username = u;
		this.password = p;
		ftpc = new FTPClient();
		ftpc.setControlKeepAliveTimeout(420);
	}
	
	public void setDownloads(){
		File f = new File(Environment.getExternalStorageDirectory()+File.separator+label+File.separator);
		f.mkdirs();
		downloadsDir = f.toString();
	}
	
	public void connect() throws AnException{
		try{
			int reply;
			ftpc.connect(serverAddress);
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
	

	@Override
	public ArrayList<String> browseUp() throws AnException {
		ArrayList<String> dirContents = new ArrayList<String>();
		try {
			ftpc.changeToParentDirectory();
			FTPFile[] files = ftpc.listFiles();
			for(int i=0;i<files.length;i++){
				dirContents.add(files[i].toString());
			}
		} catch (IOException e) {
			throw new AnException("Error: Connection is closed.",e);
		}
		return dirContents;
	}
	
	@Override
	public ArrayList<String> browsePWD() throws AnException {
		ArrayList<String> dirContents = new ArrayList<String>();
		try {
			FTPFile[] files = ftpc.listFiles();
			for(int i=0;i<files.length;i++){
				dirContents.add(files[i].toString());
			}
		} catch (IOException e) {
			throw new AnException("Error: Connection is closed.",e);
		}
		return dirContents;
	}
	
	public ArrayList<String> browse(String path) throws AnException{
		ArrayList<String> dirContents = new ArrayList<String>();
		try {
			ftpc.changeWorkingDirectory(path);	
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
		return this.type;
	}

}
