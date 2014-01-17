package kobe.angariae.connection;

import java.io.File;
import java.util.ArrayList;

import android.os.Environment;
import android.util.Log;

import kobe.angariae.Track;
import kobe.angariae.exception.AnException;

public class LocalConnection implements Connection{
	private static final String serverAddress ="LOCAL";
	private static final String username = "LOCAL";
	private static final String password = "LOCAL";
	private static final String label = "LOCAL";
	private static final String type = "LOCAL";
	private File pwd;

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
	public String getType() {
		return type;
	}

	@Override
	public void setServerAddress(String sa) {}

	@Override
	public void setUserName(String un) {}

	@Override
	public void setPassword(String p) {}

	@Override
	public void setLabel(String l) {}

	@Override
	public void connect() throws AnException {
		String extState = Environment.getExternalStorageState();
		if(!extState.equals(Environment.MEDIA_MOUNTED)){
			this.pwd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
			Log.e("public dir", pwd.toString());
			throw new AnException("External filesystem unavailable");
		}else{
			this.pwd = Environment.getExternalStorageDirectory();
			Log.e("external", pwd.toString());
//			this.pwd = Environment.getExternalStoragePublicDirectory(extState);
		}
	}

	@Override
	public void disconnect() throws AnException {
		
	}

	@Override
	public String download(String file) throws AnException {
		return file;
	}

	@Override
	public ArrayList<Track> browse(String path) throws AnException {
		ArrayList<Track> dirList = new ArrayList<Track>();
		if(path.equals("..")){
			this.pwd = pwd.getParentFile();			
		}else if(!path.equals(".")){
			this.pwd = new File(pwd, path);			
		}
		File[] pwdDirList = this.pwd.listFiles();
		if(pwdDirList != null){
			for(int i=0; i<pwdDirList.length; i++){
				Log.e("list files", pwdDirList[i].toString());
				dirList.add(new Track(pwdDirList[i]));
			}
		}
		return dirList;
	}
}
