package com.example.ejer

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.os.CountDownTimer
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_e3.*


class E3 : AppCompatActivity(), SensorEventListener {
    lateinit var sensorManager: SensorManager
    private var countDownTimer: CountDownTimer? = null
    private val timestep: TextView? = null
    private val counter = 0
    var running:Boolean = false

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (running) {
            //counter ++;
            //progressBar.setProgress(counter);
            total_steps.text = (event!!.values[0].toInt()).toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_e3)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
    override fun onResume() {
        super.onResume()
        running = true
        val stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (stepCounter!= null) {
            sensorManager.registerListener(this, stepCounter, SensorManager.SENSOR_DELAY_UI)
        } else {
            Toast.makeText(this, "No hay.. :(", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()
        running = false
        sensorManager.unregisterListener(this);
    }
}
