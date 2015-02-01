package bg.stoykov.drunk.drunkmaster;

import android.os.Bundle;



public class TimeActivity extends DrunkenMasterActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        /*SharedPreferences pref = getSharedPreferences("Lock_info", 0);
            Set<String> set = pref.getStringSet("apps", null);
            for (Iterator<String> it = set.iterator(); it.hasNext(); ) {
                String f = it.next();
                Toast selectApps = Toast.makeText(TimeActivity.this, f, Toast.LENGTH_SHORT);
                selectApps.show();
            }
         */
    }

}
