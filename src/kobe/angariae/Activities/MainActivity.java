package kobe.angariae.Activities;

import java.io.File;
import java.util.ArrayList;

import kobe.angariae.DatabaseHelper;
import kobe.angariae.R;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	
	private SQLiteDatabase db;
	private static final int NEW_CONNECTION_ID = 69;
	private static final int EDIT_CONNECTION_ID = 24;
	private ArrayAdapter<String> adapter;
	
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

        ListView listview = (ListView)findViewById(R.id.listView1);
        ArrayList<String> alist = new ArrayList<String>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, DatabaseHelper.FIELDS, null, null, null, null, null);
        if(cursor != null && cursor.moveToFirst()){
        	for(int i=0;i<cursor.getCount();i++){
        		alist.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.LABEL)));
        		cursor.moveToNext();
        	}
        }
        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, alist);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				//set destination for collection downloads
				String label = (String)parent.getItemAtPosition(position);
				File f = new File(Environment.getExternalStorageDirectory()+File.separator+label);
				f.mkdirs();
				//extract connection data from table
				Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, DatabaseHelper.FIELDS, "Label='"+label+"'", null,null,null,null);
				if(cursor != null && cursor.moveToFirst()){
					//prepare to launch activity
					Intent i = new Intent(MainActivity.this, BrowseActivity.class);
					i.putExtra("dir", f.toString());
					i.putExtra("ServerAddress", cursor.getString(cursor.getColumnIndex("ServerAddress")));
					i.putExtra("Type",cursor.getString(cursor.getColumnIndex("Type")));
					i.putExtra("Username",cursor.getString(cursor.getColumnIndex("Username")));
					i.putExtra("Password", cursor.getString(cursor.getColumnIndex("Password")));
					startActivity(i);//Launch BrowseActivity pass Connection by Intent
				}
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
    				db.execSQL("INSERT INTO "+DatabaseHelper.TABLE_NAME+" VALUES ('"+label+"','"+serverAddr+"','"
    				+type+"','"+uname+"','"+password+"');");
    				break;
    			case EDIT_CONNECTION_ID:
    				db.execSQL("UPDATE "+DatabaseHelper.TABLE_NAME+" SET "+
    						"ServerAddress="+serverAddr+
    						",Type="+type+
    						",Username="+uname+
    						",Password="+password+"WHERE Label="+label+";");
    				break;
    			default: break;
    			
    		}
    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
}
