package kobe.angariae.connection;

import java.io.File;
import java.util.ArrayList;

import android.os.Environment;

import kobe.angariae.Track;
import kobe.angariae.exception.AnException;

public class LocalConnection implements Connection{
	private String serverAddress ="LOCAL";
	private String username = "LOCAL";
	private String password = "LOCAL";
	private String label = "LOCAL";
	private String type = "LOCAL";
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
			throw new AnException("External filesystem unavailable");
		}else{
			this.pwd = Environment.getExternalStorageDirectory();
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
		this.pwd = new File(pwd, path);
		return browse();
	}

	@Override
	public ArrayList<Track> browseUp() throws AnException {
		if(pwd.getParentFile()==null){
			throw new AnException("Unable to browse to parent");
		}else{
			this.pwd = pwd.getParentFile();
		}
		return browse();
	}

	@Override
	public ArrayList<Track> browse() throws AnException {
		ArrayList<Track> dirList = new ArrayList<Track>();
		File[] pwdDirList = this.pwd.listFiles();
		if(pwdDirList != null){
			for(int i=0; i<pwdDirList.length; i++){
				dirList.add(new Track(pwdDirList[i]));
			}
		}
		return dirList;
	}

}
