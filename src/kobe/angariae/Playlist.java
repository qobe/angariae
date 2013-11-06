package kobe.angariae;

import java.util.LinkedList;
import java.util.Random;

public class Playlist {

	private int index;
	private LinkedList<String> list;
	
	public Playlist(LinkedList<String> array){
		list = new LinkedList<String>();
		for(int i=0; i<array.size(); i++){
			list.add(array.get(i));
		}
		this.index = 0;
	}
	
	
	public String getNext(){
		this.index++;
		if(this.index == this.list.size()){
			index = 0;
		}
		return list.get(index);
	}
	
	public String getPrevious(){
		this.index--;
		if(this.index < 0){
			index = list.size()-1;
		}
		return list.get(index);
	}
	
	public String getSelected(int i){
		index = i;
		return list.get(i);
	}
	public void deque(){
		list.remove();
	}
	
	public void deque(int i){
		list.remove(i);
	}
	
	public void enqueue(String track){
		list.add(track);		
	}
	public void shuffle(){
		Random r = new Random();
		LinkedList<String> temp = new LinkedList<String>();
		while(temp.size() < list.size()){
			temp.add(list.get(r.nextInt(list.size())));
		}
		this.list = temp;
	}
}
