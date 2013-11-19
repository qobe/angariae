package kobe.angariae.Activities;

import kobe.angariae.DatabaseHelper;
import kobe.angariae.R;
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
	        Bundle b = i.getExtras();
	        if(i.getAction().equals(MainActivity.CUSTOM_ACTION_EDIT)){
	        	editLabel.setText(b.getString(DatabaseHelper.LABEL), TextView.BufferType.EDITABLE);
	        	editServerAddress.setText(b.getString(DatabaseHelper.SERVER_ADDRESS), TextView.BufferType.EDITABLE);
	        	editUserName.setText(b.getString(DatabaseHelper.USER_NAME), TextView.BufferType.EDITABLE);
	        	editPassword.setText(b.getString(DatabaseHelper.PASSWORD), TextView.BufferType.EDITABLE);
	        	rGroup.check(Integer.parseInt(b.getString(DatabaseHelper.TYPE)));
	        }
	        submitButton.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					i.putExtra(DatabaseHelper.LABEL, editLabel.getText().toString());
					i.putExtra(DatabaseHelper.SERVER_ADDRESS,editServerAddress.getText().toString());
					i.putExtra(DatabaseHelper.USER_NAME,editUserName.getText().toString());
					i.putExtra(DatabaseHelper.PASSWORD,editPassword.getText().toString());
					RadioButton selectedRB = (RadioButton)rGroup.findViewById(rGroup.getCheckedRadioButtonId());
					i.putExtra(DatabaseHelper.TYPE, selectedRB.getText().toString());
					setResult(RESULT_OK, i);
					finish();
				}
	        });
	}
}
