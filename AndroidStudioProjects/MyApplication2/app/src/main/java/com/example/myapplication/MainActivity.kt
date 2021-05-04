package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter

import android.widget.ListView
import android.widget.AdapterView
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View


private lateinit var sensorManager: SensorManager

//private lateinit var listView ListView

class MainActivity : AppCompatActivity() {
    private lateinit var sensorManager: SensorManager
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val deviceSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
//        sensorManager.registerListener(MainActivity.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)

        Log.i("LOS SENSORES SON: ", ""+deviceSensors)
        println("SENSORES: " + deviceSensors)
        println("SIZE: " + deviceSensors.size)

        listView = findViewById<ListView>(R.id.recipe_list_view)
        val listItems = arrayOfNulls<String>(deviceSensors.size)
        for (i in 0 until deviceSensors.size) {
            listItems[i] = deviceSensors[i].name
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
        listView.adapter = adapter


        listView.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Get the selected item text from ListView
                val selectedItem = parent.getItemAtPosition(position) as String
                val intent = Intent(this@MainActivity, DataSensors::class.java)

                startActivity(intent)
                // Display the selected item text on TextView
//                tv.setText("Your favorite : $selectedItem")
                println("Your favorite : $selectedItem")
            }
        })

    }
}


