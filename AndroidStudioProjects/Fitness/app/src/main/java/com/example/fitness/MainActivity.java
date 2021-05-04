package com.example.fitness;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity extends  AppCompatActivity implements View.OnClickListener {
    Button stepB,absB, pesasB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);
        stepB = (Button) findViewById(R.id.step);
        absB = (Button) findViewById(R.id.abs);
        pesasB = (Button) findViewById(R.id.pesas);
        stepB.setOnClickListener(this);
        absB.setOnClickListener(this);
        pesasB.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent explicit_intent;
        switch (v.getId()) {
            case R.id.step:
                explicit_intent = new Intent(this, Estiramiento.class);
                startActivity(explicit_intent);
                break;

            case R.id.abs:
                explicit_intent = new Intent(this, Abs.class);
                startActivity(explicit_intent);
                break;

            case R.id.pesas:
                explicit_intent = new Intent(this, Pesas.class);
                startActivity(explicit_intent);
                break;
        }
    }
}
