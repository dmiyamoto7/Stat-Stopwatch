package com.ousalia.StatisticalStopwatch;


import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import com.ousalia.statstopwatch.R;

import java.util.ArrayList;

//Saved data page, should display array of time total objects, with 3 buttons: reset, Calculations, and export
public class SavedData extends AppCompatActivity {
    Button btnReset,btnCalculations, btnExport;
    public static ArrayList<Integer> timerObjs;
    private static final String FILE_NAME = "MillisecondData.txt";
    String mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_data);

        btnReset = (Button)findViewById(R.id.btnReset);     //instantiation of reset data button
        btnCalculations = (Button)findViewById(R.id.btnCalculations);   //instantiation of calculations button

        TextView DisplayString = (TextView)findViewById(R.id.stringNumsCon1);

        ArrayList<String> timerVals = (ArrayList<String>)getIntent().getSerializableExtra("timerVals");
        final ArrayList<Integer> timerObjs =  (ArrayList<Integer>)getIntent().getSerializableExtra("timerObjs");

        DisplayString.setTextSize(15);
        DisplayString.setTextColor(Color.BLACK);
        DisplayString.setMovementMethod(new ScrollingMovementMethod());


        for(int i=0; i<timerVals.size(); i++){
            DisplayString.append("Trial "+(i+1)+": "+ convertMilliInt(timerObjs.get(i)));
            DisplayString.append("\n");
        }

        

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        //button function of Calculations button
        //redirects to Calculations page
        btnCalculations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if(timerObjs.size() > 0) {
                    Intent i = new Intent(SavedData.this, Calculations.class);
                    i.putExtra("timerObjs", timerObjs);
                    startActivity(i);
                }
                else{
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            }
        });

    }





    public static CharSequence convertMilliInt(double milli){
        double timeSwapBuff=0,updateTime=0;
        double milliTruncate = 0;
        CharSequence timerSeq;
        updateTime = timeSwapBuff + milli;
        int secs = (int) (milli / 1000);
        int mins = secs / 60;
        secs %= 60;
        int milliseconds = (int) (updateTime % 1000);
        if (milliseconds<100){
            timerSeq = ("" + mins + ":" + String.format("%2d", secs) + ":0" + milliseconds);
        }
        else {
            timerSeq = ("" + mins + ":" + String.format("%2d", secs) + ":" + milliseconds);
        }
        return timerSeq;
    }


    private boolean isExternalStorageWritable(){
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            Log.i("State","Writable");
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isExternalStorageReadable(){
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())||Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())){
            Log.i("State","Readable");
            return true;
        }
        else{
            return false;
        }
    }

    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this,permission);
        return(check == PackageManager.PERMISSION_GRANTED);
    }
}





