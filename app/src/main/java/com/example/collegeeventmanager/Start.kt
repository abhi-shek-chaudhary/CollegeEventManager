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
        val act="ac"
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


               val db = Firebase.firestore
               val ref = db.collection("Users").document(id)
               ref.get().addOnSuccessListener {
                   val current = it.toObject<UserData>()
                   if (current != null) {
                       if (current.role == "Admin" || current.role=="superAdmin") {
                           post.visibility=Button.VISIBLE
                           post.setOnClickListener {
                           val intent = Intent(this, Category::class.java)
                           intent.putExtra(KEY,id)
                           intent.putExtra(act,"post")
                           startActivity(intent)
                               }
                       }
                   }
               }

           //create button interactivity
               ref.get().addOnSuccessListener {
                   val current = it.toObject<UserData>()
                   if (current != null) {
                       if (current.role == "superAdmin") {
                           create.visibility=Button.VISIBLE
                           create.setOnClickListener {
                               val intent = Intent(this, createNew::class.java)
                               startActivity(intent)
                           }
                       }
                   }
               }

           //view button interactivity
           view.setOnClickListener {
               val intent = Intent(this, Category::class.java)
               intent.putExtra(KEY,id)
               intent.putExtra(act,"view")
               startActivity(intent)

           }
       }
    }
}