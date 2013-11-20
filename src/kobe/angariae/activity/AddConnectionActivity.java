package kobe.angariae.activity;

import kobe.angariae.R;
import kobe.angariae.connection.Connection;
import kobe.angariae.connection.FTPConnection;
import kobe.angariae.connection.HTTPConnection;
import kobe.angariae.connection.ParcelableConnection;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class AddConnectionActivity extends Activity {
	private Connection c;
	@Override
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_add_connection);
	        Button submitButton = (Button)findViewById(R.id.submit_button);
	        final EditText editLabel = ((EditText)findViewById(R.id.editLabel));
	        final EditText editServerAddress = ((EditText)findViewById(R.id.editServerAddress));
	        final EditText editUserName = ((EditText)findViewById(R.id.editUserName));
	        final EditText editPassword = ((EditText)findViewById(R.id.editPassword));
	        final RadioGroup rGroup = (RadioGroup)findViewById(R.id.radioGroup1);
	        final Intent i = getIntent();
	        if(i.getAction().equals(MainActivity.CUSTOM_ACTION_EDIT)){
	        	ParcelableConnection pc = (ParcelableConnection)i.getParcelableExtra(Connection.klass);
	        	c = pc.getConnection();
	        	editLabel.setText(c.getLabel(), TextView.BufferType.NORMAL);
	        	editServerAddress.setText(c.getServerAddress(), TextView.BufferType.EDITABLE);
	        	editUserName.setText(c.getUserName(), TextView.BufferType.EDITABLE);
	        	editPassword.setText(c.getType(), TextView.BufferType.EDITABLE);
//	        	rGroup.check(Integer.parseInt(b.getString(DatabaseHelper.TYPE)));
	        }
	        submitButton.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					RadioButton selectedRB = (RadioButton)rGroup.findViewById(rGroup.getCheckedRadioButtonId());
					if(selectedRB.getText().toString().contains("FTP")){
						c = new FTPConnection();
					}else{
						c = new HTTPConnection();
					}
					c.setLabel(editLabel.getText().toString());
					c.setServerAddress(editServerAddress.getText().toString());
					c.setUserName(editUserName.getText().toString());
					c.setPassword(editPassword.getText().toString());
					ParcelableConnection pc = new ParcelableConnection(c);
					i.putExtra(Connection.klass, pc);
					setResult(RESULT_OK, i);
					finish();
				}
	        });
	}
}
