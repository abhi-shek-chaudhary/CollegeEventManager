package com.example.collegeeventmanager

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.collegeeventmanager.databinding.ActivityLoginPageBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class LoginPage : AppCompatActivity() {
    companion object{
        const val key="KEY"
    }
    private lateinit var binding: ActivityLoginPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.login.setOnClickListener {
            val id=binding.userId.text.toString().trim()
            val pass=binding.pass.text.toString().trim()
            val db= Firebase.firestore


            db.collection("Users").document(id).get().addOnSuccessListener {
                if(it.exists())
                {
                    val cur=it.toObject<UserData>()
                    if(cur?.pass.equals(pass)){
                        val intent= Intent(this, Start::class.java)
                        intent.putExtra(key,id)
                        startActivity(intent)
                        binding.userId.text?.clear()
                        binding.pass.text?.clear()
                    }
                    else{
                        Toast.makeText(this,"Password Not Correct ",Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(this,"User not found",Toast.LENGTH_SHORT).show()
                }
            }

        }

    }
}