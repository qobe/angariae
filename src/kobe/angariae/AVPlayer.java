package kobe.angariae;
import java.io.IOException;

import android.media.MediaPlayer;

public class AVPlayer {

	private MediaPlayer mp;
	
	public AVPlayer(){}
	
	public void play(String file){
		mp = new MediaPlayer();
		try {
			mp.setDataSource(file);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
	public void next(Playlist p) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException{
		mp = new MediaPlayer();
		mp.setDataSource(p.getNext());
		mp.start();
	}
	
	public void previous(Playlist p) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException{
		mp = new MediaPlayer();
		mp.setDataSource(p.getPrevious());
		mp.start();
	}
	
	public void shuffle(Playlist p){
		p.shuffle();
	}
	
	public void seekTo(){
	
	}
}
