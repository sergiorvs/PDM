package com.example.ejer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById<ListView>(R.id.recipe_list_view)
        val listItems = arrayOfNulls<String>(3)
        listItems[0] = "Ejercicio 1"
        listItems[1] = "Ejercicio 2"
        listItems[2] = "Ejercicio 3"

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
        listView.adapter = adapter

        listView.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Get the selected item text from ListView
                val selectedItem = parent.getItemAtPosition(position) as String


                if(position == 0){
                    val intent = Intent(this@MainActivity, E1::class.java)
                    startActivity(intent)
                } else if(position == 1){
                    val intent = Intent(this@MainActivity, E2::class.java)
                    startActivity(intent)
                } else{
                    val intent = Intent(this@MainActivity, E3::class.java)
                    startActivity(intent)
                }
                Toast.makeText(getApplicationContext(),
                    "You've selected: $selectedItem ",
                    Toast.LENGTH_LONG).show()
            }
        })


    }
}
