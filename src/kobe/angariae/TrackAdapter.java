package kobe.angariae;

import java.util.ArrayList;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TrackAdapter extends ArrayAdapter<Track>{
	ArrayList<Track> objects;
	
	public TrackAdapter(Context context, int tVRID, ArrayList<Track> objects){
		super(context, tVRID, objects);
		this.objects = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View v = convertView;
		
		//inflate if view is null
		if(v == null){
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.list_track, null);
		}
		Track t = objects.get(position);
		if(t != null){
			TextView textv = (TextView)v.findViewById(R.id.tracktext);
			if(textv != null){
				if(t.isDirectory()){
					textv.setText(".::"+t.getTitle());
				}else{
					textv.setText(t.getTitle());
				}
			}
		}
		return v;
	}
}
