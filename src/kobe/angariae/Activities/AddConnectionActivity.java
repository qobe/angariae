package kobe.angariae.Activities;

import kobe.angariae.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class AddConnectionActivity extends Activity {
	@Override
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        Button submit = (Button)findViewById(R.id.submit_button);
	        submit.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					Intent i = getIntent();
					i.putExtra("serverAddr", ((EditText) findViewById(R.id.editServerAddress)).getText().toString());
					i.putExtra("uname", ((EditText) findViewById(R.id.editUserName)).getText().toString());
					i.putExtra("password", ((EditText) findViewById(R.id.editPassword)).getText().toString());
					RadioGroup rGroup = (RadioGroup)findViewById(R.id.radioGroup1);
					RadioButton selectedRB = (RadioButton)rGroup.findViewById(rGroup.getCheckedRadioButtonId());
					i.putExtra("type", selectedRB.getText().toString());
					setResult(RESULT_OK, i);
					finish();
				}
	        });
	}
}
