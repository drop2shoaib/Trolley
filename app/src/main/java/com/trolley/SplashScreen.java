package com.trolley;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.parse.Parse;
import com.parse.ParseUser;
import com.trolley.utils.ApiKeys;

/**
 * The Class SplashScreen will launched at the start of the application. It will
 * be displayed for 3 seconds and than finished automatically and it will also
 * start the next activity of app.
 */
public class SplashScreen extends Activity {

    /**
     * Check if the app is running.
     */
    private boolean isRunning;

	/* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
	 */

    private void initializeParse() {
        // Enable Local Datastore.
//        Parse.enableLocalDatastore(this);
        Parse.initialize(this, ApiKeys.APPLICATION_ID, ApiKeys.CLIENT_ID);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        initializeParse();
        isRunning = true;
        startSplash();
    }

    /**
     * Starts the count down timer for 3-seconds. It simply sleeps the thread
     * for 3-seconds.
     */
    private void startSplash() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            doFinish();
                        }
                    });
                }
            }
        }).start();
    }

    /**
     * If the app is still running than this method will start the MainActivity
     * activity and finish the Splash.
     */
    private synchronized void doFinish() {
        if (isRunning) {
            isRunning = false;
            Intent intent = null;
            if (ParseUser.getCurrentUser() != null) {
                intent = new Intent(SplashScreen.this, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }
            else{
                intent = new Intent(SplashScreen.this, SignUp.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }
            startActivity(intent);
            finish();
        }
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            isRunning = false;
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}