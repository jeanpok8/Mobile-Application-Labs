package ltu.m7019e.appt4rl_view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


public class Rl_inter extends Activity {

	private TextView State;
    private Button Submitbtn;
	private ToggleButton toggle;	
	private boolean HOLD=false;
	AutoCompleteTextView AutoComplete;
	
	String[] language ={"C","C++","Java",".NET","iPhone","Android","ASP.NET","PHP","Ruby","Gmail","yahoo","Andoid","Lulea"};  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rl_inter);
		State= (TextView)findViewById(R.id.txtState);
		Submitbtn = (Button) findViewById(R.id.btnSbm);
		toggle = (ToggleButton) findViewById(R.id.tglbtn);	
		 ArrayAdapter<String> adapter = new ArrayAdapter<String> 
		 (this,android.R.layout.select_dialog_item,language); 
		 AutoComplete= (AutoCompleteTextView)findViewById(R.id.edtState); 
		 AutoComplete.setThreshold(1);//will start working from first character
		 AutoComplete.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView  
		 AutoComplete.setTextColor(Color.RED);  
		Submitbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String text;
				text = AutoComplete.getText().toString();
				if (!HOLD) {
					// Case when ToggleButton is off
					State.setText(text);
					AutoComplete.setText("");
				} else {
					Context context = getApplicationContext();
					CharSequence text1 = "Status is held!";
					int duration = Toast.LENGTH_SHORT;
					Toast toast = Toast.makeText(context, text1, duration);
					toast.show();
					

					// Case when ToggleButton is clicked
				}

			}
		});
		
		toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					// The toggle is enabled
					AutoComplete.setEnabled(false);
					HOLD = true;

				} else {
					// The toggle is disabled
					AutoComplete.setEnabled(true);
					HOLD = false;
				}
			}
		});
	}


}
