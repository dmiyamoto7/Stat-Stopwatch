package com.ousalia.StatisticalStopwatch;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.ousalia.statstopwatch.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Collections;

//Calculations page (should eventually contain all statistical analysis)
public class Calculations extends AppCompatActivity {
    public static ArrayList<Integer> timerObjs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculations);

        double mean = 0;
        double sum = 0;
        int middle = 0;
        int rangemilli=0;
        int increments=0;
        double median = 0;
        double range = 0;
        double std = 0;
        double posThreeSig = 0;
        double negThreeSig = 0;
        CharSequence meanSeq;
        CharSequence medianSeq;
        CharSequence rangeSeq;
        CharSequence stdSeq;
        CharSequence posThreeSigSeq;
        CharSequence negThreeSigSeq;
        LineGraphSeries<DataPoint> series;
        TextView DisplayMean = (TextView) findViewById(R.id.dataDisplay);
        GraphView graph = (GraphView) findViewById(R.id.graph);
        ArrayList<Integer> timerObjs = (ArrayList<Integer>) getIntent().getSerializableExtra("timerObjs");
        ArrayList<Integer> timerObjsSecs = (ArrayList<Integer>)timerObjs.clone();
        for (int i = 0; i < timerObjs.size();i++){
            timerObjsSecs.set(i, (timerObjs.get(i)/1000));
        }

        if(timerObjs.size() > 0){
            LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>();
            for (int i = 0; i < timerObjs.size();i++){
                DataPoint point = new DataPoint(i+1,timerObjsSecs.get(i));
                series1.appendData(point,true,timerObjs.size());
            }
            series1.setDrawDataPoints(true);
            series1.setDataPointsRadius(10);
            series1.setColor(Color.RED);
            series1.setThickness(3);
            graph.addSeries(series1);
        }

        //Viewport of graph
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);

        //setting viewport based on trials and max millisecond count
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(timerObjs.size()+1);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(Collections.max(timerObjsSecs)+1);


        //Axis Instantiation
        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setPadding(80);
        gridLabel.setVerticalAxisTitle("Time(sec)");
        gridLabel.setHorizontalAxisTitle("Trial");
        graph.setTitle("Time v Trial");
        gridLabel.setNumHorizontalLabels(5);
        gridLabel.setNumVerticalLabels(5);



        //bar graph instantiation
        int bucket1 = 0;
        int bucket2 = 0;
        int bucket3 = 0;
        int bucket4 = 0;
        int bucket5 = 0;
        int bucket6 = 0;
        int bucket7 = 0;
        int bucket8 = 0;
        double bucket9 = 0;
        double bucket10 = 0;

        rangemilli = Collections.max(timerObjs)-Collections.min(timerObjs);
        int incrementsMilli = rangemilli/10;
        int mini = Collections.min(timerObjs);


        //minimum second count
        int minBound = (Collections.min(timerObjs)/1000);
        //maximum second count
        int maxBound = (Collections.max(timerObjs)/1000);

        //range of data in seconds
        double rangesec = rangemilli/1000;
        //increments that define buckets

        double incrementSec = rangesec/9; //need to round up to next integer

        GraphView barGraph = (GraphView) findViewById(R.id.bGraph);

        if(timerObjs.size() > 0) {
            for (int i = 0; i < timerObjs.size(); i++) {
                if (((timerObjs.get(i)) / 1000) >= minBound && ((timerObjs.get(i)) / 1000) < (minBound + incrementSec)) {
                   bucket1++;
                }
                if (timerObjsSecs.get(i) >= (minBound + incrementSec) && (timerObjsSecs.get(i) < (minBound + (2 * incrementSec)))) {
                    bucket2++;
                }
                if (((timerObjs.get(i)) / 1000) >= (minBound + (2 * incrementSec)) && ((timerObjs.get(i)) / 1000) < (minBound + (3 * incrementSec))) {
                    bucket3++;
                }
                if (((timerObjs.get(i)) / 1000) >= (minBound + (3 * incrementSec)) && ((timerObjs.get(i)) / 1000) < (minBound + (4 * incrementSec))) {
                    bucket4++;
                }
                if (((timerObjs.get(i)) / 1000) >= (minBound + (4 * incrementSec)) && ((timerObjs.get(i)) / 1000) < (minBound + (5 * incrementSec))) {
                    bucket5++;
                }
                if (((timerObjs.get(i)) / 1000) >= (minBound + (5 * incrementSec)) && ((timerObjs.get(i)) / 1000) < (minBound + (6 * incrementSec))) {
                    bucket6++;
                }
                if (((timerObjs.get(i)) / 1000) >= (minBound + (6 * incrementSec)) && ((timerObjs.get(i)) / 1000) < (minBound + (7 * incrementSec))) {
                    bucket7++;
                }
                if (((timerObjs.get(i)) / 1000) >= (minBound + (7 * incrementSec)) && ((timerObjs.get(i)) / 1000) < (minBound + (8 * incrementSec))) {
                    bucket8++;
                }
                if (((timerObjs.get(i)) / 1000) >= (minBound + (8 * incrementSec)) && ((timerObjs.get(i)) / 1000) < (minBound + (9 * incrementSec))) {
                    bucket9++;
                }
                if (((timerObjs.get(i)) / 1000) >= (minBound + (9 * incrementSec)) && ((timerObjs.get(i)) / 1000) < (maxBound+1)) {
                    bucket10++;
                }
            }
        }



        BarGraphSeries<DataPoint> histoSeries = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(minBound, bucket1),    //Datapoint 1
                new DataPoint((minBound+incrementSec), bucket2),    //Datapoint 2
                new DataPoint(minBound+(2*incrementSec), bucket3),    //Datapoint 3
                new DataPoint(minBound+(3*incrementSec), bucket4),    //Datapoint 4
                new DataPoint(minBound+(4*incrementSec), bucket5),    //Datapoint 5
                new DataPoint(minBound+(5*incrementSec), bucket6),    //Datapoint 6
                new DataPoint((minBound+(6*incrementSec)), bucket7),    //Datapoint 7
                new DataPoint(minBound+(7*incrementSec), bucket8),    //Datapoint 8
                new DataPoint(minBound+(8*incrementSec), bucket9),    //Datapoint 9
                new DataPoint((minBound+(9*incrementSec)), bucket10)   //Datapoint 10
        });

        barGraph.addSeries(histoSeries);

        //line delineation
        histoSeries.setDrawValuesOnTop(true);
        histoSeries.setValuesOnTopColor(Color.BLACK);
        histoSeries.setColor(Color.RED);
        barGraph.addSeries(histoSeries);


        //setting viewport based on trials and max millisecond count
        barGraph.getViewport().setMinX(minBound);
        barGraph.getViewport().setMaxX(maxBound+incrementSec);
        barGraph.getViewport().setMinY(0);
        barGraph.getViewport().setMaxY(10);


        //Viewport of graph
        barGraph.getViewport().setYAxisBoundsManual(true);
        barGraph.getViewport().setXAxisBoundsManual(true);

        //Axis Instantiation
        GridLabelRenderer gridLabel1 = barGraph.getGridLabelRenderer();
        gridLabel1.setPadding(80);
        gridLabel1.setVerticalAxisTitle("Frequency");
        gridLabel1.setHorizontalAxisTitle("Spread (sec)");
        barGraph.setTitle("Histogram");
        gridLabel1.setTextSize(30);
        gridLabel1.setNumHorizontalLabels(6);
        gridLabel1.setNumVerticalLabels(5);



        //sort for sake of median
        Collections.sort(timerObjs);

        DisplayMean.setTextSize(15);
        DisplayMean.setTextColor(Color.BLACK);
        DisplayMean.setMovementMethod(new ScrollingMovementMethod());

        //mean
        for (int i = 0; i < timerObjs.size(); i++) {
            sum += timerObjs.get(i);
        }
        mean =  Math.floor((sum / timerObjs.size())*100)/100;

        //median
        middle = timerObjs.size() / 2;
        if (timerObjs.size() % 2 == 1)
            median = timerObjs.get(middle);
        else
            median = (timerObjs.get(middle - 1) + timerObjs.get(middle)) / 2;

        //range
        range = timerObjs.get(timerObjs.size()-1)-timerObjs.get(0);


        //standard deviation
        std = Math.floor(sd(timerObjs)*100)/100;
        posThreeSig = Math.floor((mean + (3*std))*100)/100;
        negThreeSig = Math.floor((mean - (3*std))*100)/100;

        //converting raw millisecond double values
        meanSeq=convertMilliInt(mean);
        rangeSeq=convertMilliInt(range);
        medianSeq=convertMilliInt(median);
        stdSeq=convertMilli(std);
        posThreeSigSeq=convertMilli(posThreeSig);
        negThreeSigSeq=convertMilli(negThreeSig);

        //display
        DisplayMean.setText("Mean:    " + meanSeq + "\n" + "Median: " + medianSeq + "\n" + "Range:   " + rangeSeq + "\n" + "\n" + "σ:          " + stdSeq+ "\n" + "3σ+:      " + posThreeSigSeq+ "\n" + "3σ-:       " + negThreeSigSeq);


    }


    public static CharSequence convertMilli(double milli){
        double timeSwapBuff=0,updateTime=0;
        double milliTruncate = 0;
        CharSequence timerSeq;
        updateTime = timeSwapBuff + milli;
        int secs = (int) (milli / 1000);
        int mins = secs / 60;
        secs %= 60;
        double milliseconds = (double) (updateTime % 1000);
        milliTruncate = Math.floor((milliseconds*100))/100;

        if (Math.abs(milliTruncate)<100) {
            if ((mins < 0) || (secs < 0) || (milliTruncate < 0)) {
                timerSeq = ("-" + Math.abs(mins) + ":" + String.format("%2d", Math.abs(secs)) + ":0" + Math.abs(milliTruncate));
            } else {
                timerSeq = (" " + mins + ":" + String.format("%2d", secs) + ":0" + milliTruncate);
            }
        }
        else{
            if ((mins < 0) || (secs < 0) || (milliTruncate < 0)) {
                timerSeq = ("-" + Math.abs(mins) + ":" + String.format("%2d", Math.abs(secs)) + ":" + Math.abs(milliTruncate));
            } else {
                timerSeq = (" " + mins + ":" + String.format("%2d", secs) + ":" + milliTruncate);
            }
        }
        return timerSeq;
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

    public static double sd(ArrayList<Integer> table) {
        int tempMean = 0;
        int tempSum = 0;
        int temp = 0;
        for (int i = 0; i < table.size(); i++) {
            tempSum += table.get(i);
        }
        tempMean = (tempSum / table.size());

        for (int i = 0; i < table.size(); i++) {
            int val = table.get(i);
            double squrDiffToMean = Math.pow(val - tempMean, 2);
            temp += squrDiffToMean;
        }
        double meanOfDiffs = (double) temp / (double) (table.size());
        return Math.sqrt(meanOfDiffs);
    }
}