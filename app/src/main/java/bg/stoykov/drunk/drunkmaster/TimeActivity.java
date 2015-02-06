package bg.stoykov.drunk.drunkmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class TimeActivity extends DrunkenMasterActionBarActivity implements View.OnClickListener {

    private EditText mTimeField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        setupScreen();
    }

    private void setHourFieldFilter(){
        mTimeField = (EditText) findViewById(R.id.etTimeActivityHours);
        mTimeField.setFilters(new InputFilter[]{new InputFilterMinMax("1", "12")});
    }

    private void setupNextStepButton(){
        Button nextStepBtn;
        nextStepBtn = (Button)findViewById(R.id.btnTimeActivityNextStep);
        nextStepBtn.setOnClickListener(this);
    }


    private void setupScreen(){
        setHourFieldFilter();
        setupNextStepButton();

    }

    private int getTimeValue(){
        String fieldValue = mTimeField.getText().toString();
        int fieldIntValue = 0;
        try {
            fieldIntValue = Integer.parseInt(fieldValue);
        }
        catch (NumberFormatException e){

        }
        return fieldIntValue;
    }

    private void saveHourValue(int hours){
        SharedPreferences pref = getSharedPreferences("Lock_info", 0);
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt("hours", hours);
        edit.apply();

    }

    private void redirectToConfirmActivity(){
        Intent in = new Intent(this, ConfirmActivity.class);
        startActivity(in);
    }

    @Override
    public void onClick(View v) {
        saveHourValue(getTimeValue());
        redirectToConfirmActivity();
    }
}
