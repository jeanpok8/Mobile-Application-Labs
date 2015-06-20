package ltu.m7019e.appt1;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/*
 * This class adds dynamic functionalities to the 
 * AppT1.
 * 
 * */

public class MainActivity extends Activity {
	public static final int[] mute_options = { 1, 0 };
	public static final int mute = 1;
	private HelloButton mHellodButton = null;
	private ByeButton mByeButton = null;

	class HelloButton extends Button {
		boolean mHellodButton = true;

		OnClickListener clicker = new OnClickListener() {
			public void onClick(View v) {

				if (mHellodButton) {
					setText("HELLO M7019E");
				} else {
					setText("BYE M7019E");
				}
				mHellodButton = !mHellodButton;
			}

		};

		public HelloButton(Context ctx) {
			super(ctx);
			setText("HELLO M7019E");
			setOnClickListener(clicker);
		}
	}

	class ByeButton extends Button {
		boolean mbyeButton = true;

		OnClickListener clicker = new OnClickListener() {
			public void onClick(View v) {

				if (mbyeButton) {
					setText("BYE M7019E");
				} else {
					setText("HELLO M7019E");
				}
				mbyeButton = !mbyeButton;
			}
		};

		public ByeButton(Context ctx) {
			super(ctx);
			setText("BYE M7019E");
			setOnClickListener(clicker);
		}
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LinearLayout ll = new LinearLayout(this);
		mHellodButton = new HelloButton(this);
		ll.addView(mHellodButton, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, 0));
		mByeButton = new ByeButton(this);
		ll.addView(mByeButton, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, 0));
		setContentView(ll);

		
	}
}
