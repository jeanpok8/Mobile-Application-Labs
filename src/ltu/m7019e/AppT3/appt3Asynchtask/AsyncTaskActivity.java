package ltu.m7019e.appt3asynctask;

import java.io.IOException;
import java.util.Date;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

/**
 * 
 * class that implements asynchronous tasks, it performs background operations
 * and publish results on the UI thread without having to manipulate threads
 * and/or handlers.
 * 
 * @author bambanza
 *
 */
public class AsyncTaskActivity extends Activity {
	Button btnSlowWork;
	// Button btnQuickWork;
	EditText etMsg;
	Long startingMillis;
	MediaPlayer mPlayer = null;
	private static final String LOG_TAG = "MediaActivity";
	private static String mFileName = "count_down.mp3";
	private boolean playing = false;

	/* Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_async_task);
		etMsg = (EditText) findViewById(R.id.EditText01);
		btnSlowWork = (Button) findViewById(R.id.Button01);
		// delete all data from database (when delete button is clicked)

		this.btnSlowWork.setOnClickListener(new OnClickListener() {
			public void onClick(final View v) {
				if (!playing) {
					new VerySlowTask().execute();
					btnSlowWork.setText("Stop the song");
					playing = true;
				} else {
					playing = false;
					btnSlowWork.setText("Start the song");
				}
			}
		});

	}

	
	private class VerySlowTask extends AsyncTask<String, Long, Void> {

		private final ProgressDialog dialog = new ProgressDialog(
				AsyncTaskActivity.this);
		private SeekBar seekBar;

		// can use UI thread here
		protected void onPreExecute() {
			startingMillis = System.currentTimeMillis();
			etMsg.setText("Playing song");
			/*
			 * this.dialog.setMessage("Wait\nSomeSLOW job is been done...");
			 * this.dialog.show();
			 */
			mPlayer = new MediaPlayer();
			try {
				mFileName = Environment.getExternalStorageDirectory().getPath();
				mFileName += "/count_down.mp3";
				Log.e(LOG_TAG, "File name: " + mFileName);
				mPlayer.setDataSource(mFileName);
				mPlayer.prepare();
				mPlayer.start();
			} catch (IOException e) {
				Log.e(LOG_TAG, "prepare() failed");
			}
			seekBar = (SeekBar) findViewById(R.id.seekBar);
			seekBar.setMax(mPlayer.getDuration());

		}

		// automatically done on worker thread (separate from UI thread)
		protected Void doInBackground(final String... args) {
			while (mPlayer.isPlaying() && playing) {
				seekBar.setProgress(mPlayer.getCurrentPosition());
			}
			return null;
		}

		// periodic updates -it is OK to change UI
		@Override
		protected void onProgressUpdate(Long... value) {
			super.onProgressUpdate(value);
			etMsg.append("\nworking..." + value[0]);
		}

		// can use UI thread here
		protected void onPostExecute(final Void unused) {
			if (this.dialog.isShowing()) {
				this.dialog.dismiss();
			}
			mPlayer.release();
			mPlayer = null;
			// cleaning-up all done
			etMsg.append("\ndone or stopped!");
		}
	}

}
