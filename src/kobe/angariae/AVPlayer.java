package kobe.angariae;
import java.io.IOException;
import java.util.LinkedList;

import kobe.angariae.Connections.Connection;
import android.media.MediaPlayer;

public class AVPlayer {

	private MediaPlayer mp;
	private Connection cn;
	private Playlist playlist;
	
	public AVPlayer(Connection conn){
		this.cn = conn;
	}
	
	public void play(String file) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException{
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
		mp.setDataSource(playlist.getNext());
		mp.start();
	}
	
	public void previous() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException{
		mp = new MediaPlayer();
		mp.setDataSource(playlist.getPrevious());
		mp.start();
	}
	
	public void shuffle(){
		playlist.shuffle();
	}
	
	public void repeat(){
		
	}
	
	public void seekTo(){
	
	}
}
