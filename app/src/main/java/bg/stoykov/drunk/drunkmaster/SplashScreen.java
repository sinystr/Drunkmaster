package bg.stoykov.drunk.drunkmaster;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;

import java.util.Date;

public class SplashScreen extends Activity {
    private HideSplashScreen task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean locked = false;
        if(currentTime() < shutdownTime()) {
            String ct = String.valueOf(currentTime());
            String st = String.valueOf(shutdownTime());
            Log.d("ct", ct);
            Log.d("st", st);
            locked = true;
        }

        task = new HideSplashScreen();
        task.execute(locked);
    }

    long shutdownTime(){
        SharedPreferences pref = getSharedPreferences("Lock_info", 0);
        return pref.getLong("shutdowntime", 0);
    }

    long currentTime(){
        Date date = new Date();
        long time = date.getTime();
        return time;
    }



    private class HideSplashScreen extends AsyncTask<Boolean, Void, Void>{


        @Override
        protected Void doInBackground(Boolean... params) {
            // Thread will sleep for 5 seconds
            boolean locked = params[0];

            try {
                Thread.sleep(3*1000);

                if(locked) {
                    Intent i = new Intent(getBaseContext(), UnlockActivity.class);
                    startActivity(i);
                }else{
                    Intent i = new Intent(getBaseContext(), HomeActivity.class);
                    startActivity(i);
                }

                //Remove activity
                finish();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            return null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("SPLASH", "onPause");
        task.cancel(true);
    }

}
