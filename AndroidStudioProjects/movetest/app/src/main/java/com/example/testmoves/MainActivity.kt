package com.example.testmoves

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.seismic.ShakeDetector
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.reflect.KMutableProperty0


class MainActivity : AppCompatActivity(), SensorEventListener, ShakeDetector.Listener {


    lateinit var sensorManager: SensorManager
    var sensor: Sensor?= null


    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun hearShake() {
//        Toast.makeText(this, "Don't shake me, bro!", Toast.LENGTH_SHORT).show()
    }

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

    override fun onSensorChanged(event: SensorEvent?) {

        val tesmove: TextView = findViewById(R.id.move) as TextView
        val coutdown: TextView = findViewById(R.id.countdown) as TextView
        var mCountDown: CountDownTimer



        ///Nota: x > (y & z) -> horizontal |____| ->
        //       y > (x & z) -> vertical |_| ->
        //       z > (x & y) -> echado ___ ->
        //       ! => < (&) <-
        val x = event!!.values[0]
        val y = event.values[1]
        val z = event.values[2]

//        if (y < 5.0 && z < 5.0 && x < -7.5){
//            //"horizontal a la derecha"
//            if(status == 0){
//                tesmove.text = "Saludos!"
//                status++
//            }
//        }else if (x < 5.0 && z < 5.0 && y > 7.5) {
//            //"VERTICAL"
//            if(status == 1){
//                tesmove.text = "Arriba"
//                flag = true
//                status++
//            }
//        }else{
//            tesmove.text = "Adios mundo cruel!"
//        }
//        else if (y < 5.0 && z < 5.0 && x > 7.5) {
//            //"horizontal a la izquierda"
//            if (status == 2) {
//                status++
//            }
//        }


          flag = rollMove(x,y,z, ::status)
          val time = (coutdown.text).toString().toInt()
//          flag = upMove(x,y,z, ::status)
          if(flag && time == 1){
              Toast.makeText(this, "themove!!!", Toast.LENGTH_SHORT).show()
              tesmove.text = "themove!!"
              mcont+=100

          } else if(status == 1){
              tesmove.text = "nothig left to move"
          } else if(status == 0){
              tesmove.text = "x_x"
          }
//        if (x > y && x > z && x > 7.5){
//            //"horizontal a la izquierda"
//            if(status == 0){
//                tesmove.text = "Saludos!"
//                status++
//            }
//        }
//        else if (x < 5.0 && z < 5.0 && y > 7.5) {
//            //"VERTICAL"
//            if(status == 1){
//                tesmove.text = "Giroooos!!!"
//                flag = true
//                status++
//            }
//        }else{
//            tesmove.text = "x_x"
//        }
        if(flag){
            flag = false
            status = 0
        }

    }

    private var gameview: ImgMove? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameview = ImgMove(this)
        setContentView(R.layout.activity_main)
//        setContentView(gameview)
        val timer = object: CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long)
            {
                countdown.text = (millisUntilFinished/1000).toString()
            }
            override fun onFinish(){
                countdown.text = "0"
            }
        }
        timer.start()

        var sM =
            getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sd = ShakeDetector(this)
        sd.start(sM)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)

    }
}
