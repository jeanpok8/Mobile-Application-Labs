package com.example.accelerometer;



import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class AnimThread extends Thread {
    
    private SurfaceHolder holder;
    private boolean running = true;
    private float x = 100;
    private float y = 100;
    private float z=100;
    private float vx = 0;
    private float vy = 0;
    private float vz = 0;
    private float tiltX = 0;
    private float tiltY = 0;
    private float tiltZ = 0;

    public AnimThread(SurfaceHolder holder) {
        this.holder = holder;
    }
    
    @Override
    public void run() {
        while(running ) {
            Canvas canvas = null;
            
            
            try {
                canvas = holder.lockCanvas();
                 synchronized (holder) {
                    // update
                    vx -= tiltX * 0.1;
                    vy += tiltY * 0.1;
                    vz+=tiltZ*0.1;
                    x += vx;
                    y += vy;
                    z+=vz;
                    if(x + 50 > canvas.getWidth()) {
                        x = canvas.getWidth() - 50;
                         vx *= -0.9;
                     }
                    
                     else if (x - 50 <0) {
                        x =50;
                        vx *= -0.9;
                    }
                     if(y + 50>canvas.getHeight()) {
                         y = canvas.getHeight() - 50;
                         vy *= -0.9;
                     }
                     else if(y - 50 < 0) {
                         y =50;
                         vy *= -0.9;
                     }
                    
                    // draw
                    canvas.drawColor(Color.BLACK);
                    Paint paint = new Paint();
                    paint.setColor(Color.WHITE);
                    canvas.drawCircle(x, y, 50, paint);
                }            
            }
            finally {
                if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
    
    

    public void setRunning(boolean b) {
        running = b;
    }
    
    public void setTilt(float tiltX, float tiltY,float tiltZ) {
        this.tiltX = tiltX;
        this.tiltY = tiltY;
        this.tiltZ=tiltZ;
    }

}
