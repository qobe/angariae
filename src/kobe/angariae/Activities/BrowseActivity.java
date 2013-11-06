package kobe.angariae.Activities;

import java.util.LinkedList;

import kobe.angariae.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class BrowseActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        
        Button upDir = (Button)findViewById(R.id.up_directory);
        upDir.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				//Launch AddConnectionActivity
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
				//AVPlayer.play(item)
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

}
