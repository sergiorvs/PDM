package com.example.justdance

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.seismic.ShakeDetector
import kotlinx.android.synthetic.main.activity_juego.*
import kotlin.reflect.KMutableProperty0


class juegoActivity : AppCompatActivity(), SensorEventListener, ShakeDetector.Listener {

    lateinit var sensorManager: SensorManager
    var sensor: Sensor?= null
    var sM: SensorManager? = null
    val sd = ShakeDetector(this)
    var shaked:Boolean = false

    val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    val id = user?.uid

    var Started:Int = 0


    var it: Int = 0
    val TIMES: IntArray = intArrayOf(9,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2)


    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun hearShake() {
//        Toast.makeText(this, "Don't shake me, bro!", Toast.LENGTH_SHORT).show()
        Log.d("shake", "dont shake me bro!")
        shaked = true
    }

//    fun shake():Boolean{
//
//        sd.start(sM)
//        sd.stop()
//        return shaked
//    }

    var status = 0
    var mcont = 0
    var flag:Boolean = false


    fun upMove(x: Float,y:Float ,z: Float, stateRef: KMutableProperty0<Int>) : Boolean{
        val state = stateRef.get()
        if (state == 0 && y < 5.0 && z < 5.0 && x < -7.5) {
            stateRef.set(stateRef.get()+1)
        }else if (state == 1 && x < 5.0 && z < 5.0 && y > 7.5){
            stateRef.set(stateRef.get()+1)
            return true
        }
        return false
    }

        //-> |_
    fun downMove(x: Float,y:Float ,z: Float, stateRef: KMutableProperty0<Int>) : Boolean{
        val state = stateRef.get()
        if (state == 0 && y < 5.0 && z < 5.0 && x < -7.5) {
            stateRef.set(stateRef.get()+1)
        }else if (state == 1 && y < x && y < z && y < 0){
            stateRef.set(stateRef.get()+1)
            return true
        }
        return false
    }

    // -> __
    fun punch(x: Float,y:Float ,z: Float, stateRef: KMutableProperty0<Int>) : Boolean{
        val state = stateRef.get()
        if (state == 0 && y > x && y > z && y > 0) {
            stateRef.set(stateRef.get()+1)
        }else if (state == 1 && z < x && z < y && z < 0){
            stateRef.set(stateRef.get()+1)
            return true
        }
        return false
    }

    fun upAndDownMove(x: Float,y:Float ,z: Float, stateRef: KMutableProperty0<Int>) : Boolean{
        val state = stateRef.get()
        if (state == 0 && y > x && y > z && y > 0) {
            stateRef.set(stateRef.get()+1)
        }else if (state == 1 && x < y && x < z && x < 0){
            stateRef.set(stateRef.get()+1)
            return true
        }
        return false
    }

    fun rollMove(x: Float,y:Float ,z: Float, stateRef: KMutableProperty0<Int>) : Boolean{
        val state = stateRef.get()
        if (state == 0 && x > y && x > z && x > 7.5) {
            stateRef.set(stateRef.get()+1)
        }else if (state == 1 && x < 5.0 && z < 5.0 && y > 7.5){
            stateRef.set(stateRef.get()+1)
            return true
        }
        return false
    }

        fun headAcheMove(x: Float,y:Float ,z: Float, stateRef: KMutableProperty0<Int>) : Boolean{
        val state = stateRef.get()
        if (state == 0 && x < y && x < z && x < 0) {
            stateRef.set(stateRef.get()+1)
        }else if (state == 1 && y > x && y > z && y > 0){
            stateRef.set(stateRef.get()+1)
            return true
        }
        return false
    }


    var toadd:Boolean = true
    override fun onSensorChanged(event: SensorEvent?) {

        val tesmove: TextView = findViewById(R.id.move) as TextView
        val coutdown: TextView = findViewById(R.id.countdown) as TextView

        var perfection = 0



        ///Nota: x > (y & z) -> horizontal |__| ->
        //       y > (x & z) -> vertical |_| ->
        //       z > (x & y) -> echado _ ->
        //       ! => < (&) <-
        val x = event!!.values[0]
        val y = event.values[1]
        val z = event.values[2]



        shaked = false
        flag = when(it){
            0, 4, 8, 12 -> punch(x,y,z, ::status)
            in 1..3, in 5..7, in 9..11, in 13..15 -> upMove(x,y,z, ::status)
            16 -> headAcheMove(x,y,z, ::status)
            17 -> rollMove(x,y,z, ::status)
            else -> false
        }

        var time: Int = 0
        if(it<TIMES.size){time = (coutdown.text).toString().toInt()}
        if(time >= 0)toadd = true
//          flag = upMove(x,y,z, ::status)
        if(flag && time == 1){
            Toast.makeText(this, "themove!!!", Toast.LENGTH_SHORT).show()
            tesmove.text = "themove!!"
            perfection = (1..2).random()
            mcont+= 100*(perfection)
            Log.d("score", mcont.toString())
            val cod =intent.getStringExtra("codigo")
            val serviceReference = FirebaseDatabase.getInstance().getReference("/servidores/$cod/players/$id")
            val serviceMessage = jugador(serviceReference.key!!, mcont.toString(), perfection.toString())
            serviceReference.setValue(serviceMessage)
        }
        if(time == 0 && toadd){
            Log.d("it", it.toString())
            it  = (it+1)%TIMES.size
            toadd = false
            if(it < TIMES.size){ onStartTimmer(TIMES[it]*1000L)}
            else{
                countdown.text = "GAME OVER"
            }
            if(status < 2){
                tesmove.text = "x_x"
                val cod =intent.getStringExtra("codigo")
                val serviceReference = FirebaseDatabase.getInstance().getReference("/servidores/$cod/players/$id")
                val serviceMessage = jugador(serviceReference.key!!, mcont.toString(), perfection.toString())
                serviceReference.setValue(serviceMessage)
//                Toast.makeText(this, "You've missed!", Toast.LENGTH_SHORT).show()
                Log.d("miss", "you missed")
            }
        }

        if(flag){
            flag = false
            status = 0
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_juego)
        val img = findViewById<ImageView>(R.id.sheep)


//        val timer = object: CountDownTimer(5000, 1000) {
//            override fun onTick(millisUntilFinished: Long)
//            {
//                countdown.text = (millisUntilFinished/1000).toString()
//            }
//            override fun onFinish(){
//                countdown.text = "0"
//            }
//        }


        val cod =intent.getStringExtra("codigo")
        val serviceReference = FirebaseDatabase.getInstance().getReference("/servidores/$cod")
        serviceReference.child("Started").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {


                Toast.makeText(applicationContext, dataSnapshot.toString(), Toast.LENGTH_LONG).show()
                Log.d("hola",dataSnapshot.value.toString())
                Started = dataSnapshot.value.toString().toInt()
                Log.d("hola2",Started.toString())
                if(Started > 0){
                    img.visibility = View.INVISIBLE
                    onStartTimmer(TIMES[0]*1000L)
                }
            }
        })


//        sM = getSystemService(Context.SENSOR_SERVICE) as SensorManager
//        val sd = ShakeDetector(this)






        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun onStartTimmer(timInMillis: Long){
        val timer = object: CountDownTimer(timInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long)
            {
                countdown.text = (millisUntilFinished/1000).toString()
            }
            override fun onFinish(){
                countdown.text = "0"
            }
        }

        timer.start()
    }

}
