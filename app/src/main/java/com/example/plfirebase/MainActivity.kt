package com.example.plfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.plfirebase.databinding.ActivityMainBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
//        auth.signOut()

        binding.btnRegister.setOnClickListener {
            registerUser()
        }
        binding.btnLogin.setOnClickListener {
            loginUser()
        }

    }

    private fun registerUser(){
        val email = binding.etEmailRegister.text.toString()
        val password = binding.etPasswordRegister.text.toString()

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
           if(it.isSuccessful){
              checkLoggedInState()
           }else{
               Log.d("raha",it.exception.toString())
           }

        }
    }

    private fun loginUser(){
        val email = binding.etEmailLogin.text.toString()
        val password = binding.etPasswordLogin.text.toString()
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
            if(it.isSuccessful){
//                val user = auth.currentUser
                checkLoggedInState()
            }else{
                Toast.makeText(this,"not logged in",Toast.LENGTH_LONG).show()

            }
        }
    }

    override fun onStart() {
        super.onStart()
//        val user = auth.currentUser
        checkLoggedInState()
    }

    private fun checkLoggedInState() {
        if (auth.currentUser == null) { // not logged in
            binding.tvLoggedIn.text = "You are not logged in"
        } else {
            binding.tvLoggedIn.text = "You are logged in!"
        }
    }


}