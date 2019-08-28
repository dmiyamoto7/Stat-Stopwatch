package com.ousalia.StatisticalStopwatch;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;

import com.ousalia.statstopwatch.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnStart,btnStop, btnCollection;
    TextView txtTimer;
    Handler customHandler = new Handler();
    ArrayList<Integer> list;
    ArrayList<String> stringNums;
    LinearLayout container, container2,container3,container4; //times;
    int n = 1;
    long startTime=0L,timeinMilliseconds=0L,timeSwapBuff=0L,updateTime=0L;

    //function for running timer
    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeinMilliseconds = SystemClock.uptimeMillis() - startTime;
            updateTime = timeSwapBuff + timeinMilliseconds;
            int secs = (int) (updateTime / 1000);
            int mins = secs / 60;
            secs %= 60;
            int milliseconds = (int) (updateTime % 1000);
            txtTimer.setText("" + mins +":"+ String.format("%2d", secs) + ":" + String.format("%3d", milliseconds));
            customHandler.postDelayed(this, 0);
        }
    };

    //Start of main
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button)findViewById(R.id.btnStart);     //instantiation of start button
        btnStop = (Button)findViewById(R.id.btnStop);       //instantiation of stop/record button
        btnCollection = (Button)findViewById(R.id.btnCollection);   //instantiation of Saved Data button
        txtTimer = (TextView)findViewById(R.id.timerValue);     //instantiation of textview that will house temporary timer values for display
        container = (LinearLayout)findViewById(R.id.container); //instantiation of invisible container used to separate rows of buttons
        container2 = (LinearLayout)findViewById(R.id.container2); //instantiation of first vertical container containing timer values
        container3 = (LinearLayout)findViewById(R.id.container3); //instantiation of second vertical container containing timer values
        container4 = (LinearLayout)findViewById(R.id.container4); //instantiation of third vertical container containing timer values
        list = new ArrayList<Integer>();                         //instantiation of array to house timetotal objects
        stringNums = new ArrayList<String>();


        //button function for start button
        //begins timer by resetting timer to zero and running updatetimerthread function
        btnStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                startTime = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimerThread,0);

            }
        });


        //button function for stop/record button
        //meant to place timetotal object into list array, add textview of timer value to respective container, and reset timer back to zero
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View time = inflater.inflate(R.layout.row,null);

                //adds first timer value into first container
                if (n==1) {
                    TextView txtValue = (TextView) time.findViewById(R.id.txtContent);   //how to display time
                    txtValue.setText(txtTimer.getText());           //sets text value of time to whatever char sequence of timer is
                    n = 2;
                    String timerString = txtTimer.getText().toString();
                    int milli = (int)(long)timeinMilliseconds;
                    list.add(milli);
                    stringNums.add(timerString);
                    container2.addView(time);
                }

                //adds second timer value into second container
                else if (n==2) {
                    TextView txtValue2 = (TextView) time.findViewById(R.id.txtContent2);   //how to display time
                    txtValue2.setText(txtTimer.getText());           //sets text value of time to whatever char sequence of timer is
                    n = 3;
                    String timerString = txtTimer.getText().toString();
                    int milli = (int)(long)timeinMilliseconds;
                    list.add(milli);
                    stringNums.add(timerString);
                    container3.addView(time);
                }

                //adds third timer value into third container
                else if (n==3) {
                    TextView txtValue3 = (TextView) time.findViewById(R.id.txtContent3);   //how to display time
                    txtValue3.setText(txtTimer.getText());           //sets text value of time to whatever char sequence of timer is
                    n = 1;
                    String timerString = txtTimer.getText().toString();
                    int milli = (int)(long)timeinMilliseconds;
                    list.add(milli);
                    stringNums.add(timerString);
                    container4.addView(time);
                }


                customHandler.removeCallbacks(updateTimerThread);
                txtTimer.setText("0:00:000");
            }
        });

        //button function for saved data button, redirects to saved data page
        btnCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent i = new Intent(MainActivity.this, SavedData.class);
                i.putExtra("timerVals", stringNums);
                i.putExtra("timerObjs", list);
                startActivity(i);

            }
        });

    }
}


