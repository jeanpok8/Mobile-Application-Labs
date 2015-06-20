package com.example.accelerometer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AnimView extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {

    private SurfaceHolder holder;
	private AnimThread animThread;
    

    public AnimView(Context context) {
    	super(context);
    	holder = getHolder();
    	holder.addCallback(this);
    	
    	SensorManager manager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
    	if(manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0) {
    		Sensor accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
    		manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    	}
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // TODO Auto-generated method stub
    }

    public void surfaceCreated(SurfaceHolder holder) {
        animThread = new AnimThread(holder);
        animThread.setRunning(true);
        animThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        animThread.setRunning(false);
        while (retry) {
            try {
                animThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }
    @Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if(animThread != null) {
	        animThread.setTilt(event.values[0], event.values[1], event.values[2]);
	    }
		
	}
    
    
    }
