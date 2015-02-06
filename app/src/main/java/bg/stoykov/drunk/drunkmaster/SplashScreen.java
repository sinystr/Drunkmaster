package bg.stoykov.drunk.drunkmaster;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;

public class SplashScreen extends Activity {
    private HideSplashScreen task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        task = new HideSplashScreen();
        task.execute();
    }



    private class HideSplashScreen extends AsyncTask<Void, Void, Void>{


        @Override
        protected Void doInBackground(Void... params) {
            // Thread will sleep for 5 seconds

            try {
                Thread.sleep(3*1000);

                // After 5 seconds redirect to another activity
                Intent i=new Intent(getBaseContext(),HomeActivity.class);
                startActivity(i);

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
