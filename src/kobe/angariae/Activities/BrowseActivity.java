package kobe.angariae.Activities;

import java.util.LinkedList;

import kobe.angariae.AVPlayer;
import kobe.angariae.Playlist;
import kobe.angariae.R;
import kobe.angariae.Connections.*;
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

public class BrowseActivity extends ListActivity{
	private Connection conn;
	private AVPlayer av = new AVPlayer();
	private ListView listview;
	private ArrayAdapter<String> adapter;
	private LinkedList<String> dirList;
	private Playlist playlist = new Playlist();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        
        Bundle b = getIntent().getExtras();
        if(b.getString("Type").equalsIgnoreCase("HTTP/S")){
        	conn = new HTTPConnection();
        }else if(b.getString("Type").equalsIgnoreCase("FTP")){
        	conn = new FTPConnection();
        }
        conn.setDownloads(b.getString("dir"));
        conn.connect(b.getString("ServerAddress"), b.getString("Username"), b.getString("Password"));
        dirList = conn.browse(".");
        
        listview = (ListView)findViewById(R.id.listView1);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dirList);
        listview.setAdapter(adapter);
        listview.setLongClickable(true);
        registerForContextMenu(listview);
        
        Button upDir = (Button)findViewById(R.id.up_directory);
        upDir.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				dirList = conn.browse("..");
				adapter.notifyDataSetChanged();
			}
        });
        
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				String item = (String)parent.getItemAtPosition(position);
				if(!item.matches("*.*")){conn.browse(item);}
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
    		playlist.enqueue(conn.download(dirList.get(info.position)));
    	}
		return true;
    }
}
