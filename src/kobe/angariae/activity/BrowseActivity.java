package kobe.angariae.activity;

import java.util.ArrayList;

import kobe.angariae.R;
import kobe.angariae.Track;
import kobe.angariae.TrackAdapter;
import kobe.angariae.connection.Connection;
import kobe.angariae.connection.ConnectionAdapter;
import kobe.angariae.connection.ParcelableConnection;
import kobe.angariae.exception.AnException;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BrowseActivity extends ListActivity{
	private ArrayList<Track> trackList;
	private TrackAdapter adapter;
	private Connection myConnection;

//	http://developer.android.com/reference/android/os/AsyncTask.html
//	subclass asynctask for all connection/network operations
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        Intent i = getIntent();
        ParcelableConnection pc = (ParcelableConnection)i.getParcelableExtra(Connection.klass);
        myConnection = pc.getConnection();
        trackList = new ArrayList<Track>();
        
        ListView lv = getListView();
        adapter = new TrackAdapter(BrowseActivity.this, R.layout.list_track, trackList);
        setListAdapter(adapter);
        lv.setLongClickable(true);
        registerForContextMenu(lv);
        
        new EstablishConnectionTask().execute(myConnection); 
        new BrowseConnectionTask().execute(".");//get listing for current directory
        
        TextView upDir = (TextView)findViewById(R.id.up_directory);
        upDir.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				new BrowseConnectionTask().execute("..");
			}
        });      
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		updateListView();
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id){
		Track item = trackList.get(position);
		if(item.isDirectory()){
			new BrowseConnectionTask().execute(item.getPath());
		}else if(item.isMusic()||item.isVideo()){
			Intent i = new Intent(BrowseActivity.this, AVPlayerActivity.class);
			i.putParcelableArrayListExtra(Track.klass, trackList);
			i.putExtra("pos", position);
			startActivity(i);
		}
	}
	
    @Override
    public void onCreateContextMenu(ContextMenu m, View v, ContextMenuInfo mi){
    	super.onCreateContextMenu(m, v, mi);
        getMenuInflater().inflate(R.menu.browse_activity_context_menu,m);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    	Track t = trackList.get(info.position); 
    	switch (item.getItemId()) {
    	case R.id.enqueue:
    		//add t to playlist
    		break;
    	case R.id.download:
    		new DownloadTask().execute(t.getTitle());
    		break;
    	}
    	return true;  
    }
    
//  using AsyncTask
    private class EstablishConnectionTask extends AsyncTask<Connection, Integer, AnException>{
    	private ProgressBar pb;
    	@Override
    	protected void onPreExecute(){
    		pb = (ProgressBar)findViewById(R.id.progressBar);
    		pb.setVisibility(View.VISIBLE);
    	}
    	
    	@Override
    	protected AnException doInBackground(Connection... conns) {
    		AnException ae = new AnException("Connection successful");
    		try {
    			conns[0].connect();
    			publishProgress(5);
    		} catch (AnException e) {
    			ae = e;
    			Log.e("EstablishConnectionTask", ae.getMessage());
    		}
    		return ae;
    	}
    	
    	@Override
    	protected void onProgressUpdate(Integer... progress){
    		super.onProgressUpdate(progress[0]);
    		pb.setProgress(progress[0]);
    	}
    	
    	@Override
    	protected void onPostExecute(AnException ae){
    		pb.setProgress(0);
    		pb.setVisibility(View.GONE);
    	}
    }
     
    private class BrowseConnectionTask extends AsyncTask<String, Void, ArrayList<Track>>{
		@Override
		protected ArrayList<Track> doInBackground(String... paths) {
			ArrayList<Track> list = null;
			try {
				list = myConnection.browse(paths[0]);
			} catch (AnException e) {
				Log.e("BrowseToConnectionTask", e.getMessage());
			}
			return list;
		}
		
		@Override
		protected void onPostExecute(ArrayList<Track> a){
			trackList = a;
			updateListView();
		}
    }
    
    private class DownloadTask extends AsyncTask<String, Integer, String>{
		@Override
		protected String doInBackground(String... filenames) {
			String path = null;
			try {
				path = myConnection.download(filenames[0]);
			} catch (AnException e) {
				Log.e("DownloadTask", e.getMessage());
			}
			return path;
		}
    }
    
    private void updateListView(){
    	TrackAdapter ta = (TrackAdapter)getListAdapter();
    	ta.notifyDataSetChanged();
    }
}
