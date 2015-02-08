package bg.stoykov.drunk.drunkmaster;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;


public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        PreferencesController preferences = new PreferencesController(this);
        HideSplashScreen task = new HideSplashScreen();
        task.execute(preferences.isLockTimeNotOver());
    }


    private class HideSplashScreen extends AsyncTask<Boolean, Void, Void>{

        @Override
        protected Void doInBackground(Boolean... params) {
            // Thread will sleep for 5 seconds
            boolean locked = params[0];

            try {
                Thread.sleep(3*1000);
                // If the app is still locked we redirect to the unlocking activity
                // otherwise we redirect the user to the home activity
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

}
