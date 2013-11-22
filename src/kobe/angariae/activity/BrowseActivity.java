package kobe.angariae.activity;

import java.util.ArrayList;

import kobe.angariae.R;
import kobe.angariae.Track;
import kobe.angariae.TrackAdapter;
import kobe.angariae.connection.Connection;
import kobe.angariae.connection.ParcelableConnection;
import kobe.angariae.exception.AnException;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.TextView;

public class BrowseActivity extends ListActivity{
	private ArrayList<Track> tracks;
	private TrackAdapter adapter;
	private Connection c;

//	http://developer.android.com/reference/android/os/AsyncTask.html
//	subclass asynctask for all connection/network operations
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        Intent i = getIntent();
        ParcelableConnection pc = (ParcelableConnection)i.getParcelableExtra(Connection.klass);
        c = pc.getConnection();
        tracks = new ArrayList<Track>();
        try {
			c.connect();
			tracks = c.browse();
		} catch (AnException e) {
			e.makeToast(BrowseActivity.this);
		}

        ListView lv = getListView();
        adapter = new TrackAdapter(BrowseActivity.this, R.layout.list_track, tracks);
        setListAdapter(adapter);
        lv.setLongClickable(true);
        registerForContextMenu(lv);
        
        TextView upDir = (TextView)findViewById(R.id.up_directory);
        upDir.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				try {
					tracks = c.browseUp();
					adapter.notifyDataSetChanged();
				} catch (AnException e) {
					e.makeToast(BrowseActivity.this);
				}
			}
        });      
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id){
		Track item = tracks.get(position);
		if(item.isDirectory()){
			try {
				tracks = c.browse(item.getName());
				adapter.notifyDataSetChanged();
			} catch (AnException e) {
				e.makeToast(BrowseActivity.this);
			}
		}else if(item.isMusic()||item.isVideo()){
//			av.play(item);
		}
	}
	
    @Override
    public void onCreateContextMenu(ContextMenu m, View v, ContextMenuInfo mi){
    	super.onCreateContextMenu(m, v, mi);
        AdapterContextMenuInfo info = (AdapterContextMenuInfo)mi;
        m.add(0, 1, 0, "Enque");
        m.add(0, 2, 0, "Download");
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    	Track t = tracks.get(info.position);
    	switch (item.getItemId()) {
    	case 1:
    		//add t to playlist
    		break;
    	}
		return true;
    }
}
