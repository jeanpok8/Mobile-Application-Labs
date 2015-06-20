package ltu.m7019e.appt3typewriter;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Menu;
import android.widget.TextView;

public class TypeWriterActivity extends Activity {
	  private Typewriter writer = null;
	   @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_type_writer);
	        Typewriter writer = new Typewriter(this);
	        setContentView(writer);

	        //Add a character every 150ms
	        writer.setCharacterDelay(150);
	        writer.animateText();
	    }
}

