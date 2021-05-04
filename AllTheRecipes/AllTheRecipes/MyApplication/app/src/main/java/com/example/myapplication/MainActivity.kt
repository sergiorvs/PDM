package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var sensorManager: SensorManager
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val deviceSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)

        Log.i("LOS SENSORES SON: ", ""+deviceSensors)

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


                if(position == 0){
                    val intent = Intent(this@MainActivity, Accelerometer::class.java)
                    startActivity(intent)
                } else if(position == 1){
                    val intent = Intent(this@MainActivity, Proximity::class.java)
                    startActivity(intent)
                } else{
                    val intent = Intent(this@MainActivity, Grip::class.java)
                    startActivity(intent)
                }


                // Display the selected item text on TextView
//                tv.setText("Your favorite : $selectedItem")
                Toast.makeText(getApplicationContext(),
                    "You've selected: $selectedItem ",
                    Toast.LENGTH_LONG).show()
//                println("Your favorite : $selectedItem")
            }
        })
    }
}
