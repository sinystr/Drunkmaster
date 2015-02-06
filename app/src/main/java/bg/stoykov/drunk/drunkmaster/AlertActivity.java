package bg.stoykov.drunk.drunkmaster;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class AlertActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
        background.start();

    }

    Thread background = new Thread(new Runnable() {

        public void run() {
            try {
                Thread.sleep(3*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message msgObj = handler.obtainMessage();
            Bundle b = new Bundle();
            b.putString("message", "close");
            msgObj.setData(b);
            handler.sendMessage(msgObj);
        }

        private final Handler handler = new Handler() {

            public void handleMessage(Message msg) {

                String aResponse = msg.getData().getString("message");

                if (aResponse.equals("close")) {

                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    startActivity(intent);
                }




            }
        };

    });



}
