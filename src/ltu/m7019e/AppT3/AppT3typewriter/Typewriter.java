package ltu.m7019e.appt3typewriter;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.AttributeSet;
import android.widget.TextView;

import static java.lang.Thread.sleep;

public class Typewriter extends TextView {


    private long mDelay = 500; //Default 500ms delay
    Shared1 s = new Shared1();
    private HandlerThread mHandler2 = new HandlerThread("hghg");
    private Handler mHandler3 = new Handler();
    
    public Typewriter(Context context) {
        super(context);
        mHandler2.start();

    }

    public Typewriter(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHandler2.start();
    }

    private Handler mHandler = new Handler();
    private Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            int i;
            for (i = 0; i < 1000; i++){
                if(i%60==0) {
                    append(Character.toString(".X".charAt(s.dif())));
                    try {
                        sleep(9);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }


            mHandler.postDelayed(characterAdder, 20);
            mHandler3.post(bumpMode);

        }
    };

	public void animateText() {

		// Handler3 will call the bump
		mHandler3 = new Handler(mHandler2.getLooper());
		mHandler.removeCallbacks(characterAdder);
		mHandler3.removeCallbacks(bumpMode);
		mHandler3.post(bumpMode);
		mHandler.postDelayed(characterAdder, 20);
	}


    private Runnable bumpMode = new Runnable() {
        @Override
        public void run() {
            try {
                while (true) {
                    s.bump();
                    //append("a");
                    sleep(20);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };



    public void setCharacterDelay(long millis) {
        mDelay = millis;
    }
}
