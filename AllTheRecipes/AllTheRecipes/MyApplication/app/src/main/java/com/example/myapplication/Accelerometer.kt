package com.example.myapplication

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_accelerometer.*
import java.util.*


class Accelerometer : AppCompatActivity(), SensorEventListener {
    lateinit var sensorManager: SensorManager

    var sensor: Sensor ?= null



    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    var xold = 0.0
    var yold = 0.0
    var zold = 0.0
    var oldtime:Long = 0
    var threshold = 1000.0

    override fun onSensorChanged(event: SensorEvent?) {
        var x = event!!.values[0]
        var y = event.values[1]
        var z = event.values[2]

        var text = "No cuentes con eso"



        val currTime = System.currentTimeMillis()
        if( (currTime - oldtime) > 100 ){
            var timeDiff = currTime - oldtime
            oldtime = currTime
            var speed = Math.abs(x+y+z-xold-yold-zold)/timeDiff*10000
            if(speed > threshold){
                var v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                v.vibrate(500)

                val r = Random()
                val n = r.nextInt(4)
                if(n == 0) text = "Si"
                if(n == 1) text = "No"
                if(n == 2) text = "Pregunta Despues"
                if(n == 3) text = "No cuentes con eso"

                accelerometer.text = text
//                Toast.makeText(applicationContext, text, Toast.LENGTH_LONG).show()

            }
        }
//        accelerometer.text = "Si"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accelerometer)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)


    }

    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
//        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }

}
