package com.example.collegeeventmanager

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.collegeeventmanager.databinding.MainActicityBinding
import com.example.collegeeventmanager.ui.theme.CollegeEventManagerTheme
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
    companion object{
        const val key="KEY"
    }
    private lateinit var binding: MainActicityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= MainActicityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.alreadyAccount.setOnClickListener {
            val intent= Intent(this, LoginPage::class.java)
            startActivity(intent)
        }
        binding.signUp.setOnClickListener {
            val id=binding.usedId.text.toString().trim()
            val pass=binding.pass.text.toString().trim()
            val name=binding.name.text.toString().trim()
            val user= UserData(name,id,pass)
            var db= Firebase.firestore
            val ref1=db.collection("Users").document(id)
            ref1.get().addOnSuccessListener {
                if(it.exists())
                {
                    Toast.makeText(this,"User Already exist", Toast.LENGTH_SHORT).show()

                }
                else {
                    ref1.set(user)
                    val intent= Intent(this,Start::class.java)
                    intent.putExtra(key,id)
                    startActivity(intent)

                }
            }.addOnFailureListener {
                Toast.makeText(this,"cant Fetch DAta", Toast.LENGTH_SHORT).show()
            }
            binding.usedId.text?.clear()
            binding.pass.text?.clear()
           binding.name.text?.clear()
        }

    }
}

