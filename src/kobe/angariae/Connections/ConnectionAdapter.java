package kobe.angariae.Connections;

import java.util.ArrayList;

import kobe.angariae.R;
import kobe.angariae.R.id;
import kobe.angariae.R.layout;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ConnectionAdapter extends ArrayAdapter<Connection>{
	private ArrayList<Connection> objects;
	
	public ConnectionAdapter(Context context, int textViewResourceId, ArrayList<Connection> objects){
		super(context, textViewResourceId, objects);
		this.objects = objects;
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		View v = convertView;
		
		//inflate if view is null
		if(v == null){
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.list_connection, null);
		}
		Connection c = objects.get(position);
		if(c != null){
			TextView tt = (TextView)v.findViewById(R.id.toptext);
			TextView bt = (TextView)v.findViewById(R.id.bottomtext);
			TextView mt = (TextView)v.findViewById(R.id.midtext);
			if(tt != null){
				tt.setText(c.getLabel());
			}
			if(bt != null){
				bt.setText(c.getServerAddress());
			}
			if(tt != null){
				mt.setText(c.getType());
			}
		}
		return v;
	}
}
