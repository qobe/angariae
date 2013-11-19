package kobe.angariae.Activities;

import java.io.File;
import java.util.ArrayList;

import kobe.angariae.DatabaseHelper;
import kobe.angariae.R;
import kobe.angariae.Connections.Connection;
import kobe.angariae.Connections.ConnectionAdapter;
import kobe.angariae.Connections.FTPConnection;
import kobe.angariae.Connections.HTTPConnection;
import kobe.angariae.exception.AnException;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	
	
	private SQLiteDatabase db;
	private static final int NEW_CONNECTION_ID = 69;
	private static final int EDIT_CONNECTION_ID = 24;
	public static final String CUSTOM_ACTION_EDIT = "kobe.angariae.Activities.ACTION_EDIT_CONNECTION";
	private ArrayList<Connection> Connections;
	
	private void toast(String msg){
		Toast t = Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG);
		t.show();	
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
	    DatabaseHelper dHelper = new DatabaseHelper(MainActivity.this);
	    db = dHelper.getWritableDatabase();
        
        Button b = (Button)findViewById(R.id.new_connection);
        b.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, AddConnectionActivity.class);
				startActivityForResult(i, NEW_CONNECTION_ID);
			}
        });

        ListView listview = getListView();
        Connections = new ArrayList<Connection>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, DatabaseHelper.FIELDS, null, null, null, null, null);
        if(cursor != null && cursor.moveToFirst()){
        	Connection c;
        	for(int i=0;i<cursor.getCount();i++){
        		if(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TYPE)).equals("FTP")){
        			c = new FTPConnection();
        		}else{
        			c = new HTTPConnection();
        		}
        		c.setLabel(cursor.getString(cursor.getColumnIndex(DatabaseHelper.LABEL)));
        		c.setServerAddress(cursor.getString(cursor.getColumnIndex(DatabaseHelper.SERVER_ADDRESS)));
        		c.setUserName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_NAME)));
        		c.setPassword(cursor.getString(cursor.getColumnIndex(DatabaseHelper.PASSWORD)));
        		cursor.moveToNext();
        	}
        }
        ConnectionAdapter adapter = new ConnectionAdapter(MainActivity.this, android.R.layout.simple_list_item_1, Connections);
        listview.setAdapter(adapter);
        registerForContextMenu(listview);
        
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				//set destination for collection downloads
				File f = new File(Environment.getExternalStorageDirectory()+File.separator+Connections.get(position).getLabel());
				f.mkdirs();
				//prepare to launch activity
				Intent i = new Intent(MainActivity.this, BrowseActivity.class);
				i.putExtra("dir", f.toString());
//				i.putExtra("CLASS", Connections.get(position));
				startActivity(i);//Launch BrowseActivity pass Connection by Intent
			}
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position,long id){
//				ContextMenu{Edit, Delete}
				return false;
			}
		});
        
    }
 
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	super.onActivityResult(requestCode, resultCode, data);
    	if(resultCode == RESULT_OK){
    		Bundle b = data.getExtras();
    		String label = b.getString(DatabaseHelper.LABEL);
    		String serverAddr = b.getString(DatabaseHelper.SERVER_ADDRESS);
    		String type = b.getString(DatabaseHelper.TYPE);
    		String uname = b.getString(DatabaseHelper.USER_NAME);
    		String password = b.getString(DatabaseHelper.PASSWORD);
    		switch(requestCode){
    			case NEW_CONNECTION_ID:
    				db.execSQL(String.format("INSERT INTO %s VALUES ('%s','%s','%s','%s','%s');",
    						DatabaseHelper.TABLE_NAME, label, serverAddr, type, uname, password));
    				break;
    			case EDIT_CONNECTION_ID:
    				db.execSQL(String.format("UPDATE %s SET %s=%s,%s=%s,%s=%s,%s=%s WHERE %s=%s;",
    						DatabaseHelper.TABLE_NAME, DatabaseHelper.SERVER_ADDRESS, serverAddr,
    						DatabaseHelper.TYPE, type, DatabaseHelper.USER_NAME, uname,
    						DatabaseHelper.PASSWORD, password, DatabaseHelper.LABEL, label));
    				break;
    			default: break;
    			
    		}
    	}
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
    	super.onCreateContextMenu(menu, v, menuInfo);
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo)menuInfo;
    	menu.add(0, 1, 0, "Edit connection...");
    	menu.add(0, 2, 1, "Delete connection...");
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    	switch (item.getItemId()) {
    	case 1:// launch edit connection activity
    		Intent i = new Intent(MainActivity.this, AddConnectionActivity.class);
			i.setAction(CUSTOM_ACTION_EDIT);
			Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, DatabaseHelper.FIELDS, 
					"Label='"+info.id+"'", null,null,null,null);
			i.putExtra(DatabaseHelper.SERVER_ADDRESS, cursor.getString(cursor.getColumnIndex(DatabaseHelper.SERVER_ADDRESS)));
			i.putExtra(DatabaseHelper.TYPE, cursor.getString(cursor.getColumnIndex(DatabaseHelper.TYPE)));
			i.putExtra(DatabaseHelper.USER_NAME,cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_NAME)));
			i.putExtra(DatabaseHelper.PASSWORD, cursor.getString(cursor.getColumnIndex(DatabaseHelper.PASSWORD)));
			startActivityForResult(i, EDIT_CONNECTION_ID);
			break;
    	case 2:
    		//delete connection: remove from DB
    		break;
    	}
		return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
}
