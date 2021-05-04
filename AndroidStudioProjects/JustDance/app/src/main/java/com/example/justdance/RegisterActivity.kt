package com.example.justdance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    companion object{
        val TAG = "register"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register_button_register.setOnClickListener{
            performRegister()
        }

        already_have_account_text_View.setOnClickListener{
            Log.d("register" , "Try to show login activity")

            ///lunch the login activity
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun performRegister(){
        val email = email_edittext_register.text.toString()
        val password = password_edittext_register.text.toString()

        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Please enter text in email/pw",Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("register" , "Email is: " + email)
        Log.d("register" , "Password is: ${password}")

        /// Firebase Authentication to create a user whit email and password
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                if (!it.isSuccessful) return@addOnCompleteListener

                //else if successful
                Log.d("register", "Successfuly created user with uid ${it.result?.user?.uid}")

//                uploadImageToFirebaseStorage()
                saveUserToFirebaseDatabase()

            }
            .addOnFailureListener{
                Log.d("register", "Fail to create user ${it.message}")
                Toast.makeText(this,"Fail to create user ${it.message}",Toast.LENGTH_SHORT).show()
            }
    }

//    private fun uploadImageToFirebaseStorage(){
//        if (selectedPhotoUrl == null) return
//
//        val filename = UUID.randomUUID().toString()
//        val ref =FirebaseStorage.getInstance().getReference("/image/$filename")
//
//        ref.putFile(selectedPhotoUrl)
//            .addOnSuccessListener {
//                Log.d("register", "Successfully load image: ${it.metadata?.path}")
//
//                ref.downloadUrl.addOnSuccessListener {
//                    Log.d("register", "Fail location $it")
//                }
//            }
//    }

    private fun saveUserToFirebaseDatabase(){
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid,username_edittext_register.text.toString())
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("register", "Fanaly we saved the user to Firebase Databes")

                val intent = Intent(this,LatesMessagesActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .addOnFailureListener{
                Log.d(TAG, "Failed to set value to data base: ${it.message}")
            }
    }
}
@Parcelize
class User(val uid: String, val username: String ): Parcelable{
    constructor(): this("","")
}
