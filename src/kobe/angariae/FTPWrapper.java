package kobe.angariae;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FTPWrapper {

	/**
	 * private class globals
	 */
	private FTPClient ftpc;
	private String downloadsDir;
	
	/**
	 * Empty constructor to ease use of ftpclient
	 */
	public FTPWrapper(){
		ftpc = new FTPClient();
	}
	
	/**
	 * Set the directory that will be used to contain downloads
	 **/
	public void setDownloadDestination(String path){
		downloadsDir = path;
	}
	
	/**
	 * initiates the ftp connection to the server
	 * @param serverAddr
	 * @param username
	 * @param password
	 */
	public void establishConnection(String serverAddr, String username, String password){
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

	/**
	 * closes ftp connection to server
	 */
	public void closeConnection(){
		try {
			ftpc.disconnect();
		} catch (IOException e) {
			//errors disconnecting
			e.printStackTrace();
		}
	}
	
	/**
	 * returns a list of files in ftp present working dir
	 * to be used for displaying contents of a directory
	 * @return
	 */
	public LinkedList<String> getDirListing(){
		LinkedList<String> dirContents = new LinkedList<String>();
		try {
			FTPFile[] files = ftpc.listFiles();
			for(int i=0;i<files.length;i++){
				dirContents.add(files[i].toString());
			}
		} catch (IOException e) {
			//ftp reply code 421, connection closed, try re-establishing
			e.printStackTrace();
		}
		return dirContents;
	}
	
	/**
	 * simple ftp command to change directory
	 * @param path
	 */
	public void changeDir(String path){
		try {
			ftpc.changeWorkingDirectory(path);
		} catch (IOException e) {
			// premature connection closure
			e.printStackTrace();
		}
	}
	
	/**
	 * retrieves the file specified by filename 
	 * and downloads it to path specified in downloadsDir
	 * @param filename
	 */
	public String downloadFile(String filename){
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
