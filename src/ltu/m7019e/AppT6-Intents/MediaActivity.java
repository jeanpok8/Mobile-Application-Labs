package ltu.m7019e.appt2mediarecorder;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;

/**
 * Class that extends the Media recoder and add the recording using external MIC
 * recorder nad play the recorded voice using the external player
 * 
 * @author 
 */

public class MediaActivity extends Activity implements OnClickListener,
		OnCompletionListener {

	public static int RECORD_REQUEST = 0;

	Button createRecording, playRecording;

	Uri audioFileUri;

	/* Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_media);

		createRecording = (Button) this.findViewById(R.id.RecordButton);
		createRecording.setOnClickListener(this);
		playRecording = (Button) this.findViewById(R.id.PlayButton);
		playRecording.setOnClickListener(this);
		playRecording.setEnabled(false);
	}

	/*
	 * Method which contains the first intent used to record voice using an
	 * external Mic
	 */

	public void onClick(View v) {
		if (v == createRecording) {
			Intent intent = new Intent(
					MediaStore.Audio.Media.RECORD_SOUND_ACTION);
			startActivityForResult(intent, RECORD_REQUEST);
		} else if (v == playRecording) {

		}
	}

	/*
	 * Method which is called when the subsquent activity is done (Record a
	 * voice using an external Mic recorder).
	 */

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == RECORD_REQUEST) {
			audioFileUri = data.getData();
			playRecording.setEnabled(true);
			String select = data.getDataString();
			Intent myActivity2 = new Intent(android.content.Intent.ACTION_VIEW);
			Uri data2 = Uri.parse(select);
			String type = "audio/m4a";
			myActivity2.setDataAndType(data2, type);
			startActivity(myActivity2);

		}
	}

	public void onCompletion(MediaPlayer mp) {
		playRecording.setEnabled(true);
	}
}
