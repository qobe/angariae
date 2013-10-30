package kobe.angariae;
import java.io.IOException;
import java.util.LinkedList;
import android.media.MediaPlayer;

public class AVPlayer {

	private MediaPlayer mp;
	private FTPWrapper ftpw;
	private int indexp;
	private LinkedList<String> playlist;
	
	public AVPlayer(FTPWrapper fw){
		this.ftpw = fw;
	}
	
	public void play(String file) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException{
		playlist = ftpw.getDirListing();
		indexp = 0;
		mp = new MediaPlayer();
		String path = ftpw.downloadFile(file);
		mp.setDataSource(path);
		mp.prepare();
		mp.start();
	}

	public void start(){
		mp.start();
	}
	
	public void stop(){
		mp.stop();
	}
	
	public void pause(){
		mp.pause();
	}
	
	public void next() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException{
		mp = new MediaPlayer();
		mp.setDataSource(playlist.get(indexp));
		mp.start();
		indexp++;
	}
	
	public void previous() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException{
		indexp--;
		mp = new MediaPlayer();
		mp.setDataSource(playlist.get(indexp));
		mp.start();
	}
	
	public void shuffle(){
		
	}
	
	public void repeat(){
		
	}
	
	public void seekTo(){
	
	}
	
	public LinkedList<String> getPlaylist(){
		return this.playlist;
	}
	
	public void addToPlaylist(String file){
		
	}
}
