package kobe.angariae;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Playlist {
	public static final String klass = "playlist";
	private int index;
	private ArrayList<Track> playlist;
	
	public Playlist(ArrayList<Track> list, int i){
		this.playlist = list;
		this.index = i;
		
	}
	
	public Playlist(ArrayList<Track> list){
		this.playlist = list;
	}
	
	public Track getNext(){
		this.index = (index+1)%playlist.size();
		return playlist.get(index);
	}
	
	public Track getPrevious(){
		this.index = (index-1)%playlist.size();
		return playlist.get(index);
	}
	
	public Track getCurrent(){
		return playlist.get(index);
	}
	
	public Track get(int i){
		index = i;
		return playlist.get(i);
	}

	public void dequeue(int i){
		playlist.remove(i);
	}
	
	public void enqueue(Track track){
		playlist.add(track);		
	}
	
	public void shuffle(){
		Random r = new Random();
		ArrayList<Track> temp = new ArrayList<Track>();
		while(temp.size() < playlist.size()){
			temp.add(playlist.get(r.nextInt(playlist.size())));
		}
		this.playlist = temp;
	}
}
