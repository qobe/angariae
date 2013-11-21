package kobe.angariae.activity;

import java.util.ArrayList;

import kobe.angariae.AVPlayer;
import kobe.angariae.DatabaseHelper;
import kobe.angariae.Playlist;
import kobe.angariae.R;
import kobe.angariae.connection.Connection;
import kobe.angariae.connection.FTPConnection;
import kobe.angariae.connection.HTTPConnection;
import kobe.angariae.connection.ParcelableConnection;
import kobe.angariae.exception.AnException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class BrowseActivity extends Activity{
	private Connection c;
	private AVPlayer av;
	private ListView listview;
	private ArrayAdapter<String> adapter;
	private ArrayList<String> dirList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        Intent i = getIntent();
        ParcelableConnection pc = (ParcelableConnection)i.getParcelableExtra(Connection.klass);
        c = pc.getConnection();
        av = new AVPlayer();
        
        
        new Thread(new Runnable(){
        	public void run(){
        		Bundle b = getIntent().getExtras();
        		try {
					c.connect();
//					dirList = conn.browse(".");
				} catch (AnException e) {
					e.makeToast(BrowseActivity.this);
				}
        	}
        }).start();
        dirList = new ArrayList<String>();

        listview = (ListView)findViewById(R.id.browseListView);
        adapter = new ArrayAdapter<String>(BrowseActivity.this, android.R.layout.simple_list_item_1, dirList);
        listview.setAdapter(adapter);
        listview.setLongClickable(true);
        registerForContextMenu(listview);
        
        TextView upDir = (TextView)findViewById(R.id.up_directory);
        upDir.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				try {
					dirList = c.browse("..");
				} catch (AnException e) {
					e.makeToast(BrowseActivity.this);
				}
				adapter.notifyDataSetChanged();
			}
        });
        
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				String item = (String)parent.getItemAtPosition(position);
				if(!item.matches("*.*")){
					try {
						c.browse(item);
					} catch (AnException e) {
						e.makeToast(BrowseActivity.this);
					}
				}
//				else av.play(item); //stream from server?
//				if(item.matches("*.(3gp)|(mp4)|(m4a)|(aac)|(3gp)|(flac)|(mp3)|(wav)|(mid)|(xmf)|(mxmf)|(rtttl)|(rtx)|(ogg)|(mkv)|(ota)|(imy)"))
			}
        });       
	}
	
    @Override
    public void onCreateContextMenu(ContextMenu m, View v, ContextMenuInfo mi){
    	super.onCreateContextMenu(m, v, mi);
        AdapterContextMenuInfo info = (AdapterContextMenuInfo)mi;
        m.add(0, 1, 0, "Add to playlist");
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    	switch (item.getItemId()) {
    	case 1:

    		break;
    	}
		return true;
    }
}
