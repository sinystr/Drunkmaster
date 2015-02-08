package bg.stoykov.drunk.drunkmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class QuestionActivity extends ActionBarActivity implements View.OnClickListener {

    Button mAnswerBtn;
    EditText mAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        initAnswerBtn();
        initAnswer();

    }

    private void initAnswerBtn(){
        mAnswerBtn = (Button)findViewById(R.id.btnUnlockActivityAnswer);
        mAnswerBtn.setOnClickListener(this);
    }

    private void initAnswer(){
        mAnswer = (EditText)findViewById(R.id.etQuestionActivityAnswer);
    }

    private String getAnswer(){
        return mAnswer.getText().toString();
    }


    void nullShutdownPrefTime(){
        PreferencesController controller = new PreferencesController(this);
        controller.nullShutdownPrefTime();
    }

    private void unlock(){
        Intent in = new Intent(this, BlockingService.class);
        stopService(in);
        nullShutdownPrefTime();
        in = new Intent(this, HomeActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(in);
        Toast.makeText(getApplicationContext(), getString(R.string.correct_answer),
                Toast.LENGTH_SHORT).show();

        finish();
    }

    @Override
    public void onClick(View v) {
        if(getAnswer().equals("brother") || getAnswer().equals("Brother")){
            unlock();
        }else{
            Toast.makeText(getApplicationContext(), getString(R.string.wrong_answer),
                    Toast.LENGTH_SHORT).show();

        }
    }
}
