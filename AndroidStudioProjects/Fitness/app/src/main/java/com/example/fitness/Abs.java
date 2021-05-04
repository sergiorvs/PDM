package com.example.fitness;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Abs extends AppCompatActivity  {
    Sensor gyroscopeSensor;
    SensorManager sensorManager;
    private Button BtnStop  ;
    private Button BtnStart  ;
    private Button Btnrs  ;
    private TextView TvSteps;
    private TextView vTitle;
    ImageView image;
    SensorEventListener gyroscopeSensorListener;
    String history="";
    int absC=0;
    boolean absT = true;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.step);
            sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

            gyroscopeSensor =
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        TvSteps = (TextView) findViewById(R.id.tv_steps);
        image = (ImageView) findViewById(R.id.image);
        BtnStart = (Button) findViewById(R.id.btn_start);
        Btnrs = (Button) findViewById(R.id.btn_restart);
        BtnStop = (Button) findViewById(R.id.btn_stop);
        vTitle = (TextView) findViewById(R.id.tv_title);
        vTitle.setText("Abdominales");

        gyroscopeSensorListener = new SensorEventListener() {

            @Override
            public void onAccuracyChanged (Sensor sensor,int accuracy){
            }

            @Override
            public void onSensorChanged (SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

                    history+=("x: "+ Float.toString(event.values[0])+'\n');
                    history+=("y: "+ Float.toString(event.values[1])+'\n');
                    history+=("z: "+ Float.toString(event.values[2])+'\n');

                    Log.d("x: ", Float.toString(event.values[0]));

                    //Log.d("y: ",Float.toString(event.values[1]));
                    //Log.d("z: ", Float.toString(event.values[2]));
                    vTitle.setText(Float.toString(event.values[0]));


                    if (event.values[0] > 0.5f) { // anticlockwise
                        absT = false;
                        //getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                        //image.setImageResource(R.drawable.flechad);

                    } else if (event.values[0] <= -0.5f) { // clockwise
                        if (absT==false)
                        {
                            absC +=1;
                            TvSteps.setText(Integer.toString(absC));
                            //getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                            //image.setImageResource(R.drawable.flechai);

                            absT=true;

                        }
                    }

                }
            }
        };

        BtnStart.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            absC = 0;


            sensorManager.registerListener(gyroscopeSensorListener ,gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);

        }

    });

            Btnrs.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    absC = 0;
                    TvSteps.setText(Integer.toString(absC));

                }

            });

        BtnStop.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {

            sensorManager.unregisterListener(gyroscopeSensorListener);
            try {
                writeFile(history);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    });
    }

    public static void writeFile(String text) throws IOException {
        File fout = new File("out.txt");
        FileOutputStream fos = new FileOutputStream(fout);
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        osw.write(text);
        osw.close();
    }

}
