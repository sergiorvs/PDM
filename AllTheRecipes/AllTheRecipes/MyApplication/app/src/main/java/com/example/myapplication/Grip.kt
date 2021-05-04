package com.example.myapplication

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_grip.*
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.ImageView


class Grip : AppCompatActivity(), SensorEventListener {
    private var sensorManager: SensorManager? = null
    private var mPresureSensor: Sensor? = null

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        val values = event!!.values
        pressure.text = String.format("%.3f mbar", values[0])
        val img = findViewById<ImageView>(R.id.houses)

        if(values[0] > 400.0){
            img.setImageResource(R.drawable.alto)
        }else{
            img.setImageResource(R.drawable.bajo)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grip)

        sensorManager = applicationContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager?
        mPresureSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_PRESSURE)


    }

    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(this, mPresureSensor, SensorManager.SENSOR_DELAY_UI)
//        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }


}
