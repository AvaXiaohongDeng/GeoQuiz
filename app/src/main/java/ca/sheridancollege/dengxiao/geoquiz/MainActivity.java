/*
 * Name: Xiaohong Deng
 * Student ID: 991517517
 * Assignment: Quiz App
 * May 08, 2021
 *
 * Version2:
 * Add the lifecycle methods to save and restore my data in order to make sure
 * no data is lost when the screen is rotated
 * @author dengxiao
 * */

package ca.sheridancollege.dengxiao.geoquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ca.sheridancollege.dengxiao.geoquiz.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="MainActivity";

    private ActivityMainBinding binding;
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_oceans,true),
            new Question(R.string.question_mideast,false),
            new Question(R.string.question_africa,false),
            new Question(R.string.question_americas,true),
            new Question(R.string.question_asia,true),
    };

    private int mCurrentIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate(Bundle) called");

/*        //way1.2 process rotation to retrieve
        if(savedInstanceState !=null){
            mCurrentIndex = savedInstanceState.getInt("save1",0);
        }*/

        //inflating widgets and putting them on screen
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //getting references to inflated widgets
        mTrueButton = binding.btnTrue;
        mFalseButton = binding.btnFalse;
        mNextButton = binding.btnNext;
        mPrevButton = binding.btnPrev;
        mQuestionTextView = binding.tvQuestion;

        updateQuestion();

        //setting listeners on widgets to handle user interaction
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //click true button, then
                checkAnswer(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //click false button, then
                checkAnswer(false);
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex +1 ) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentIndex ==0){
                    Toast.makeText(MainActivity.this,R.string.noprev_toast,Toast.LENGTH_SHORT).show();
                } else {
                    mCurrentIndex = (mCurrentIndex -1 ) % mQuestionBank.length;
                    updateQuestion();
                }
            }
        });

        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex +1 ) % mQuestionBank.length;
                updateQuestion();
            }
        });
    }

    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){

        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;
        if(userPressedTrue == answerIsTrue){
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(MainActivity.this,messageResId,Toast.LENGTH_LONG).show();
    }

    //lifecycle methods
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestory() called");
    }

/*    //way 1: process rotation
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.i(TAG,"onSaveInstanceState() was called");
        outState.putInt("save1", mCurrentIndex);
    }*/

    //way2.1 process rotation to save
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.i(TAG,"onSaveInstanceState() was called");
        outState.putCharSequence("save1", mQuestionTextView.getText());
    }

    //way2.2 process rotation to retrieve
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.i(TAG, "onRestoreInstanceState() was called");
        mQuestionTextView.setText(savedInstanceState.getCharSequence("save1"));
    }
}