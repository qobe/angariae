package kobe.angariae;
import java.io.IOException;
import java.util.LinkedList;

import android.media.MediaPlayer;
import android.net.Uri;

public class AVPlayer {

	private MediaPlayer mp;
	//private String[] playlist;
	private int indexp;
	private LinkedList<String> playlist;
	
	public AVPlayer(String[] dirContents){
		this.playlist = new LinkedList();
		for(int i=0; i<dirContents.length;i++){
			this.playlist.add(dirContents[i]);
		}
		this.indexp = 0;
	}
	
	public void play(String file) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
		mp = new MediaPlayer();
		mp.setDataSource(file);
		mp.start();
	}
	
	public void play() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException{
		mp = new MediaPlayer();
		mp.setDataSource(playlist.get(indexp));
		mp.start();
		indexp++;
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
}
