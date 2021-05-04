package com.example.justdance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_sala.*
import java.lang.ref.Reference


class SalaActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sala)



        ingresar.setOnClickListener{
            ingresr()
        }


    }

    fun ingresr()
    {
        val cod = codigo.text.toString()

        if (cod.isEmpty()){
            Toast.makeText(this,"Please enter text codigo", Toast.LENGTH_SHORT).show()
            return
        }

        val user:FirebaseUser? = FirebaseAuth.getInstance().currentUser
        val id = user?.uid


        val reference = FirebaseDatabase.getInstance().getReference("/servidores/$cod/players/$id")

        val ServiceMessage = jugador(reference.key!!, "0", "0")

        reference.setValue(ServiceMessage)
            .addOnSuccessListener {
                val intent = Intent(this,juegoActivity::class.java)
                intent.putExtra("codigo",cod)
                startActivity(intent)
            }

        var nPlayerss = 1
        FirebaseDatabase.getInstance().getReference("/servidores/$cod").child("nPlayers").setValue(nPlayerss)
        FirebaseDatabase.getInstance().getReference("/servidores/$cod").child("Started").setValue(0)
//        FirebaseDatabase.getInstance().getReference("/servidores/$cod").child("players").addValueEventListener(object: ValueEventListener{
//            override fun onCancelled(p0: DatabaseError) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                nPlayerss = dataSnapshot.childrenCount.toInt()
//            }
//            })




    }


}

class jugador(val id: String,val puntaje: String , val perfeccion : String){
    constructor() :this("","","")
}
