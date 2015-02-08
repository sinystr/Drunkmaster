package bg.stoykov.drunk.drunkmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class UnlockActivity extends DrunkenMasterActionBarActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock);

        initUnlockButton();

    }

    void initUnlockButton(){
        Button unlockButton = (Button)findViewById(R.id.btnUnlockActivityUnlock);
        unlockButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent in = new Intent(this, QuestionActivity.class);
        startActivity(in);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
