package com.example.accelerometer;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;


public class MainActivity extends Activity implements SensorEventListener {

	private boolean mInitialized;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;

	private final String fileName = "statistic.txt";
	private File outFile;
	private FileOutputStream fos;
	private OutputStreamWriter out;
	private BufferedWriter bwriter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mInitialized = false;

		// Instantiate SensorManager
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		// Get Accelerometer sensor
		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {

			outFile = new File(
					Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
					fileName);
		}
		try {
			/* Create and open necessary stream for writing information */
			fos = new FileOutputStream(outFile);
			out = new OutputStreamWriter(fos);
			bwriter = new BufferedWriter(out);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {

		super.onResume();
		// register Listener for SensorManager and Accelerometer sensor
		mSensorManager.registerListener(this, mAccelerometer,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onPause() {

		super.onPause();
		// unregister Listener for SensorManager
		mSensorManager.unregisterListener(this);
		try {
			bwriter.close();
			out.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// can be safely ignored for tutorial
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
                TextView tvX = (TextView) findViewById(R.id.x_axis);
		TextView tvY = (TextView) findViewById(R.id.y_axis);
		TextView tvZ = (TextView) findViewById(R.id.z_axis);
		// TODO Please read and write to views measurements from other axles

		float x;
		float y;
		float z;
		// TODO Write measurements of the accelerometer to x, y and z
		x = event.values[0];
		y = event.values[0];
		z = event.values[0];

		if (!mInitialized) {
			tvX.setText("0.0");
			mInitialized = true;

		} else {
			// TODO Set new value into corresponding TextViews
			tvX.setText(Float.toString(x));
			tvY.setText(Float.toString(y));
			tvZ.setText(Float.toString(z));

		}

		try {
			outstr += Float.toString(event.values[0]) + "   "
					+ Float.toString(event.values[1]) + "    "
					+ Float.toString(event.values[2]);
			bwriter.write(outstr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

