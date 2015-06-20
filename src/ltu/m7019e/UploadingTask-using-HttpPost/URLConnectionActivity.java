package ltu.m7019e.appt8urlconnection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import ltu.m7019e.appt3asynctask.R;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * Class that implements file upload functionalities on ec2 amazon web services
 * 
 * @author bambanza
 */
public class URLConnectionActivity extends Activity {
	Button btnSlowWork;
	Button btnQuickWork;
	EditText etMsg;
	Long startingMillis;
	HttpURLConnection connection = null;
	DataOutputStream outputStream = null;
	DataInputStream inputStream = null;
	private FileOutputStream fos;
	private static File outFile = null;
	String urlServer = "http://52.17.253.59/upload.php";
	String lineEnd = "\r\n";
	String twoHyphens = "--";
	String boundary = "*****";
	int bytesRead, bytesAvailable, bufferSize;
	byte[] buffer;
	int maxBufferSize = 1 * 1024 * 1024;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_async_task);
		etMsg = (EditText) findViewById(R.id.EditText01);
		btnSlowWork = (Button) findViewById(R.id.Button01);
		// delete all data from database (when delete button is clicked)
		this.btnSlowWork.setOnClickListener(new OnClickListener() {
			public void onClick(final View v) {
				new VerySlowTask().execute();
			}
		});

	
	}

	private class VerySlowTask extends AsyncTask<String, Long, Void> {

		private final ProgressDialog dialog = new ProgressDialog(
				URLConnectionActivity.this);

		// can use UI thread here
		protected void onPreExecute() {
			startingMillis = System.currentTimeMillis();
			etMsg.setText("Start Time: " + startingMillis);
			this.dialog.setMessage("Wait\nUploading is on the way...");
			this.dialog.show();
		}

		// automatically done on worker thread (separate from UI thread)
		protected Void doInBackground(final String... args) {
			try {
				FileInputStream fileInputStream = new FileInputStream(new File(
						Environment.getExternalStorageDirectory(),
						"count_down.mp3"));

				URL url = new URL(urlServer);
				connection = (HttpURLConnection) url.openConnection();

				// Allow Inputs &amp; Outputs.
				connection.setDoInput(true);
				connection.setDoOutput(true);
				connection.setUseCaches(false);

				// Set HTTP method to POST.
				connection.setRequestMethod("POST");

				connection.setRequestProperty("Connection", "Keep-Alive");
				connection.setRequestProperty("Content-Type",
						"multipart/form-data;boundary=" + boundary);

				outputStream = new DataOutputStream(
						connection.getOutputStream());
				outputStream.writeBytes(twoHyphens + boundary + lineEnd);
				// outputStream.writeBytes("Conte5nt-Disposition: form-data; name=\"uploadedfile\";filename=\""
				// + mFileName +"\"" + lineEnd);
				outputStream
						.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\""
								+ "count_down.mp3" + "\"" + lineEnd);
				outputStream.writeBytes(lineEnd);

				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];

				// Read file
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				while (bytesRead > 0) {
					outputStream.write(buffer, 0, bufferSize);
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);
				}

				outputStream.writeBytes(lineEnd);
				outputStream.writeBytes(twoHyphens + boundary + twoHyphens
						+ lineEnd);

				// Responses from the server (code and message)
				int serverResponseCode = connection.getResponseCode();
				String serverResponseMessage = connection.getResponseMessage();

				fileInputStream.close();
				outputStream.flush();
				outputStream.close();

			} catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
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
			// cleaning-up all done
			etMsg.append("\nEndTime:"
					+ (System.currentTimeMillis() - startingMillis) / 1000);
			etMsg.append("\ndone!");
		}
	}

}
