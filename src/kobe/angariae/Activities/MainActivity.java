package kobe.angariae.Activities;

import java.io.File;
import java.util.LinkedList;

import kobe.angariae.DatabaseHelper;
import kobe.angariae.R;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	
	private static String TABLE_NAME = "Connections";
	private String[] columns = {"Label","ServerAddress","Type","Username","Password"};
	private SQLiteDatabase db;
	private LinkedList<String> list;
	private static final int NEW_CONNECTION_ID = 69;

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
	    DatabaseHelper dHelper = new DatabaseHelper(MainActivity.this);
	    db = dHelper.getWritableDatabase();
	    
	    Toast t = Toast.makeText(this, db.getPath(), Toast.LENGTH_LONG);
        t.show();
        
        Button newConnection = (Button)findViewById(R.id.new_connection);
        newConnection.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, AddConnectionActivity.class);
				startActivityForResult(i, NEW_CONNECTION_ID);
			}
        });

        ListView listview = (ListView)findViewById(R.id.listView1);
        LinkedList<String> list = new LinkedList<String>();
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        if(cursor != null && cursor.moveToFirst()){
        	for(int i=0;i<cursor.getCount();i++){
        		list.add(cursor.getString(cursor.getColumnIndex(columns[0])));
        		cursor.moveToNext();
        	}
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				//set destination for collection downloads
				String label = (String)parent.getItemAtPosition(position);
				File f = new File(Environment.getExternalStorageDirectory()+File.separator+label);
				f.mkdirs();
				//extract connection data from table
				Cursor cursor = db.query(TABLE_NAME, columns, "Label="+label, null,null,null,null);
				//prepare to launch activity
				Intent i = new Intent(getApplicationContext(), BrowseActivity.class);
				i.putExtra("dir", f.toString());
				i.putExtra("ServerAddress", cursor.getString(cursor.getColumnIndex("ServerAddress")));
				i.putExtra("Type",cursor.getString(cursor.getColumnIndex("Type")));
				i.putExtra("Username",cursor.getString(cursor.getColumnIndex("Username")));
				i.putExtra("Password", cursor.getString(cursor.getColumnIndex("Password")));
				startActivity(i);//Launch BrowseActivity pass Connection by Intent
			}
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position,long id){
//				parent.removeView(view); //remove item from display
//				db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE Label="+list.get(position)+";");//delete from database
				return false;
			}
		});
        
    }
    
    @Override
    protected void onResume(){
    	
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	super.onActivityResult(requestCode, resultCode, data);
    	if(resultCode == RESULT_OK && requestCode == NEW_CONNECTION_ID){
    		Bundle b = data.getExtras();
    		String label = b.getString("label");
    		String serverAddr = b.getString("serverAddr");
    		String type = b.getString("type");
    		String uname = b.getString("uname");
    		String password = b.getString("password");
    		db.execSQL("INSERT INTO "+TABLE_NAME+" VALUES ('"+label+"','"+serverAddr+"','"
    				+type+"','"+uname+"','"+password+"');");
    		//refresh listView; listadapter.notifyDataSetChanged()
    	}
    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
}
