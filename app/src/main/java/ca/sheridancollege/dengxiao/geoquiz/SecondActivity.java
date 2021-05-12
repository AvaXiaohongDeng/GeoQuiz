package ca.sheridancollege.dengxiao.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ca.sheridancollege.dengxiao.geoquiz.databinding.ActivityMainBinding;
import ca.sheridancollege.dengxiao.geoquiz.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TURE = "ca.sheridancollege.dengxiao.geoquiz.answer_is_true";
    private  static final String EXTRA_ANSWER_SHOWN = "ca.sheridancollege.dengxiao.geoquiz.answer_shown";

    private boolean mAnswerIsTrue;
    private TextView mTvAnswer;
    private Button mBtnShowAnswer;

    private static final String TAG = "SecondActivity";

    private ActivitySecondBinding binding;

    //to allow us to create an Intent properly configured with the extra SecondActivity will need
    public static Intent newIntent(Context packageContext, boolean answerIsTure){
        Intent i = new Intent(packageContext,SecondActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TURE,answerIsTure);
        return i;
    }

    //to decode the extra into something MainActivity can use.
    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN,false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");

        //inflating widgets and putting them on screen
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //retrieve the value from the extra
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TURE,false);

        mTvAnswer = binding.tvAnswer;
        mBtnShowAnswer = binding.btnShowAnswer;

        //process button
        mBtnShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAnswerIsTrue){
                    mTvAnswer.setText(R.string.true_button);
                }else{
                    mTvAnswer.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
            }
        });
    }

    //sending back an intent
    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN,isAnswerShown);
        //setting a result
        setResult(RESULT_OK,data);
    }
}