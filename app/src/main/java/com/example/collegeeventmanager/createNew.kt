package com.example.collegeeventmanager

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects

class createNew : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_new)
        var db= Firebase.firestore
        val id=findViewById<TextInputEditText>(R.id.userid)
        val btn=findViewById<Button>(R.id.makeadmin)
         btn.setOnClickListener {
             val id2=id.text.toString().trim()
             db.collection("Users").document(id2).update("role","Admin").addOnSuccessListener {
                 Toast.makeText(this,"Admin Update successfully",Toast.LENGTH_SHORT).show()
             }.addOnFailureListener {
                 Toast.makeText(this,"Admin not made ",Toast.LENGTH_SHORT).show()
             }


         }

    }
}


