package bg.stoykov.drunk.drunkmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends DrunkenMasterActionBarActivity implements View.OnClickListener {

    protected Button mLockButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setListenerToLockButton();
    }
    void setListenerToLockButton(){
        mLockButton = (Button)findViewById(R.id.btnHomeActivityLock);
        mLockButton.setOnClickListener(this);
    }
    void startAppsActivity(){
        Intent in = new Intent(this, AppsActivity.class);
        startActivity(in);
    }

    @Override
    public void onClick(View v) {
        startAppsActivity();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}