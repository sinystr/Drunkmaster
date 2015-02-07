package bg.stoykov.drunk.drunkmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class ConfirmActivity extends DrunkenMasterActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        setupHoursConfirmText();
        setupButtonListener();
    }

    protected int getHours(){
         SharedPreferences pref = getSharedPreferences("Lock_info", 0);
         return pref.getInt("hours", 0);


    }

    protected String getHoursWord(){
        if(getHours() > 1){
            return "hours";
        }

        return "hour";
    }

    protected void setupHoursConfirmText(){
        TextView tv = (TextView)findViewById(R.id.tvConfirmActivityNHours);
        String text = "<font color=#ffffff>" + getHours() + "</font> <font color=#ee5217>"+ getHoursWord() +"</font>";
        tv.setText(Html.fromHtml(text));
    }

    protected void setupButtonListener(){
        Button confirm = (Button)findViewById(R.id.btnConfirmActivityConfirm);
        confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent in = new Intent(this, BlockingService.class);
        startService(in);
        Toast.makeText(getApplicationContext(), "Drunkmaster activated!!",
                Toast.LENGTH_SHORT).show();
        in = new Intent(this, UnlockActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(in);

        finish();
    }
}
