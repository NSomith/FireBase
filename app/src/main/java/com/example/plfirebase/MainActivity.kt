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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var auth:FirebaseAuth
    private val personalcollectionRef = Firebase.firestore.collection("person")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnUploadData.setOnClickListener {
            val name =binding.etFirstName.text.toString()
            val last = binding.etLastName.text.toString()
            val age =binding.etAge.text.toString()
            val person = Person(name,last,age)
            savePerson(person)

        }
        //to get our real time data whenever we update the data
        subscribeToRealtimeUpdates()

    }

    private fun subscribeToRealtimeUpdates() {
        personalcollectionRef.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            firebaseFirestoreException?.let {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                return@addSnapshotListener
            }
            querySnapshot?.let {
                val sb = StringBuilder()
                for(document in it) {
                    val person = document.toObject(Person::class.java)
                    sb.append("$person\n")
                }
                binding.tvPersons.text = sb.toString()
            }
        }
    }

    private fun savePerson(person:Person){
        personalcollectionRef.add(person).addOnSuccessListener {
            Log.d("ast","Success")
        }
                .addOnFailureListener {
                    Log.d("ast",it.toString())
                }
    }

}