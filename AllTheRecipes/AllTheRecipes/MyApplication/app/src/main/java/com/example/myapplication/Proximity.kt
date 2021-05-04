package com.example.myapplication

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_proximity.*

class Proximity : AppCompatActivity() {
    private var sensorManager: SensorManager? = null
    private var mProximitySensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proximity)

        sensorManager = applicationContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager?
        mProximitySensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_PROXIMITY)


        sensorManager!!.registerListener(proximitySensorEventListener, mProximitySensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    private var proximitySensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

        }

        override fun onSensorChanged(event: SensorEvent) {
            if (event.sensor.type == Sensor.TYPE_PROXIMITY) {

                val img = findViewById<ImageView>(R.id.imageView)

                if (event.values[0] <= 2f) {
                    proximity.text = "TOO NEAR"
//                    img.visibility = View.VISIBLE
                    img.setImageResource(R.drawable.near)

//                    Toast.makeText(getApplicationContext(),
//                        "Too Near ",
//                        Toast.LENGTH_LONG).show()
                } else {
                    proximity.text = "TOO FAR"
//                    img.visibility = View.INVISIBLE
                    img.setImageResource(R.drawable.far)
//                    Toast.makeText(getApplicationContext(),
//                        "Too Far ",
//                        Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
