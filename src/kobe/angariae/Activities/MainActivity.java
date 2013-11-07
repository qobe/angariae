package kobe.angariae.Activities;

import java.util.LinkedList;

import kobe.angariae.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	private static final String PATH_TO_DB = "/sdcard/mediaslayer/Connections";
	private SQLiteDatabase db;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteDatabase.openDatabase(PATH_TO_DB, null, db.CREATE_IF_NECESSARY);
        
        Button newConnection = (Button)findViewById(R.id.new_connection);
        newConnection.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				//Launch AddConnectionActivity
				//Intent i = new Intent(getApplicationContext(), AddConnectionActivity.class);
				//startActivityForResult(i, 69);
				//store Intent fields on return
			}
        });
        
        ListView listview = (ListView)findViewById(R.id.listView1);
        LinkedList<String> list = new LinkedList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				String item = (String)parent.getItemAtPosition(position);
				//SELECT uname, passwd FROM Connections WHERE name = item;
				//c = new Connection()
				//c.connect(serverAddr, uname, passwd)
				//Launch BrowseActivity pass Connection by Intent
			}
        	
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position,long id){
				parent.removeView(view); //remove item from display
				//delete from database
				return false;
			}
		});
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	super.onActivityResult(requestCode, resultCode, data);
    	
    }
    
}
