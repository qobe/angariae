package kobe.angariae.Activities;

import java.util.ArrayList;
import java.util.LinkedList;

import kobe.angariae.AVPlayer;
import kobe.angariae.DatabaseHelper;
import kobe.angariae.Playlist;
import kobe.angariae.R;
import kobe.angariae.Connections.*;
import kobe.angariae.exception.AnException;
import android.app.Activity;
import android.app.ListActivity;
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

public class BrowseActivity extends Activity{
	private Connection conn;
	private AVPlayer av;
	private ListView listview;
	private ArrayAdapter<String> adapter;
	private ArrayList<String> dirList;
	private Playlist playlist = new Playlist();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        
        Bundle b = getIntent().getExtras();
        if(b.getString(DatabaseHelper.TYPE).contains("HTTP")){
        	conn = new HTTPConnection();
        }else {
        	conn = new FTPConnection();
        }
        
        final String serverAdd = b.getString(DatabaseHelper.SERVER_ADDRESS);
        final String uname = b.getString(DatabaseHelper.USER_NAME);
        final String passwd = b.getString(DatabaseHelper.PASSWORD);
        conn.setDownloads(b.getString("dir"));       		
        av = new AVPlayer();
        
        
        new Thread(new Runnable(){
        	public void run(){
        		Bundle b = getIntent().getExtras();
        		try {
					conn.connect();
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
        
        Button upDir = (Button)findViewById(R.id.up_directory);
        upDir.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				try {
					dirList = conn.browse("..");
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
						conn.browse(item);
					} catch (AnException e) {
						e.makeToast(BrowseActivity.this);
					}
				}
//				else av.play(item); //stream from server?
//				if(item.matches("*.(3gp)|(mp4)|(m4a)|(aac)|(3gp)|(flac)|(mp3)|(wav)|(mid)|(xmf)|(mxmf)|(rtttl)|(rtx)|(ogg)|(mkv)|(ota)|(imy)"))
			}
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position,long id){
//				Context menu: 1.Add to playlist?
				return false;
			}
		});
        
	}
	
    @Override
    public void onCreateContextMenu(ContextMenu m, View v, ContextMenuInfo mi){
    	super.onCreateContextMenu(m, v, mi);
        AdapterContextMenuInfo info = (AdapterContextMenuInfo)mi;
        m.add(0, 1, 0, "Add to playlist...");
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    	switch (item.getItemId()) {
    	case 1:
    		//add item selected to playlist
    		try {
				playlist.enqueue(conn.download(dirList.get(info.position)));
			} catch (AnException e) {
				e.makeToast(BrowseActivity.this);
			}
    		break;
    	}
		return true;
    }
}
