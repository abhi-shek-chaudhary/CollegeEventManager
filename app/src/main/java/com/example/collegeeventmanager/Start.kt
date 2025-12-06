package com.example.collegeeventmanager

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.key
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class Start : AppCompatActivity() {
    companion object{
        val KEY="key"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_start)
       val post=findViewById<Button>(R.id.post)
        val view=findViewById<Button>(R.id.view)
        val create =findViewById<Button>(R.id.createNewAdmin)
        val id=intent.getStringExtra(MainActivity.key)
       if(id!=null) {
           //post button interactivity
           post.setOnClickListener {
               val db = Firebase.firestore
               val ref = db.collection("Users").document(id)
               ref.get().addOnSuccessListener {
                   val current = it.toObject<UserData>()
                   if (current != null) {
                       if (current.role == "Admin") {
                           val intent = Intent(this, posting::class.java)
                           intent.putExtra(KEY,id)
                           startActivity(intent)
                       } else {
                           Toast.makeText(this, "Only Admin can post", Toast.LENGTH_SHORT).show()
                       }
                   }
               }
           }
           //create button interactivity
           create.setOnClickListener {
               val db = Firebase.firestore
               val ref = db.collection("Users").document(id)
               ref.get().addOnSuccessListener {
                   val current = it.toObject<UserData>()
                   if (current != null) {
                       if (current.role == "Admin") {
                           val intent = Intent(this, createNew::class.java)
                           startActivity(intent)
                       } else {
                           Toast.makeText(this, "Only Admin can post", Toast.LENGTH_SHORT).show()
                       }
                   }
               }
           }
           //view button interactivity
           view.setOnClickListener {
               val intent = Intent(this, View_page::class.java)
               startActivity(intent)

           }
       }
    }
}