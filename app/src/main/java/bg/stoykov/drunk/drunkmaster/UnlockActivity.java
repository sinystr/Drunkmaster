package bg.stoykov.drunk.drunkmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class UnlockActivity extends DrunkenMasterActionBarActivity implements View.OnClickListener{
    Button unlockButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock);

        initUnlockButton();

    }

    void initUnlockButton(){
        unlockButton = (Button)findViewById(R.id.btnUnlockActivityUnlock);
        unlockButton.setOnClickListener(this);
    }

    void nullShutdownPrefTime(){
        SharedPreferences pref = getSharedPreferences("Lock_info", 0);
        SharedPreferences.Editor edit = pref.edit();
        long shutdownTime = 0;
        edit.putLong("shutdowntime", shutdownTime);
        edit.apply();
    }

    @Override
    public void onClick(View v) {
        nullShutdownPrefTime();
        Intent in = new Intent(this, BlockingService.class);
        stopService(in);
        in = new Intent(this, HomeActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(in);

        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
