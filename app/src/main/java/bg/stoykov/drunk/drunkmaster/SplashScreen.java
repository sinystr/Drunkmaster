package bg.stoykov.drunk.drunkmaster;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        HideSplashScreen hide = new HideSplashScreen();
        hide.execute();

    }

    // We start new Thread that will redirect us to the Home Activity

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

}
