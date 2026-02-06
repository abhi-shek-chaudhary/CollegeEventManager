package com.example.collegeeventmanager

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.collegeeventmanager.databinding.ActivityCategoryBinding

class Category : AppCompatActivity() {
    lateinit var bind: ActivityCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind= ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(bind.root)

        var action=intent.getStringExtra(Start.act)
        val id=intent.getStringExtra(Start.KEY)
        if(action=="post"){
            bind.sports.setOnClickListener {
                val intent= Intent(this, posting::class.java)
                intent.putExtra("cat","sports")
                intent.putExtra("id",id)
                startActivity(intent)
            }

            bind.fashion.setOnClickListener {
                val intent= Intent(this, posting::class.java)
                intent.putExtra("cat","fashion")
                intent.putExtra("id",id)
                startActivity(intent)
            }

            bind.technical.setOnClickListener {
                val intent= Intent(this, posting::class.java)
                intent.putExtra("cat","technical")
                intent.putExtra("id",id)
                startActivity(intent)
            }

            bind.careerfair.setOnClickListener {
                val intent= Intent(this, posting::class.java)
                intent.putExtra("cat","careerfair")
                intent.putExtra("id",id)
                startActivity(intent)
            }

            bind.cultural.setOnClickListener {
                val intent= Intent(this, posting::class.java)
                intent.putExtra("cat","cultural")
                intent.putExtra("id",id)
                startActivity(intent)
            }

            bind.other.setOnClickListener {
                val intent= Intent(this, posting::class.java)
                intent.putExtra("cat","other")
                intent.putExtra("id",id)
                startActivity(intent)
            }
        }
        if(action=="view"){
            bind.sports.setOnClickListener {
                val intent= Intent(this, View_page::class.java)
                intent.putExtra("cat","sports")
                intent.putExtra("id",id)
                startActivity(intent)
            }

            bind.fashion.setOnClickListener {
                val intent= Intent(this, View_page::class.java)
                intent.putExtra("cat","fashion")
                intent.putExtra("id",id)
                startActivity(intent)
            }

            bind.technical.setOnClickListener {
                val intent= Intent(this, View_page::class.java)
                intent.putExtra("cat","technical")
                intent.putExtra("id",id)
                startActivity(intent)
            }

            bind.careerfair.setOnClickListener {
                val intent= Intent(this, View_page::class.java)
                intent.putExtra("cat","careerfair")
                intent.putExtra("id",id)
                startActivity(intent)
            }

            bind.cultural.setOnClickListener {
                val intent= Intent(this, View_page::class.java)
                intent.putExtra("cat","cultural")
                intent.putExtra("id",id)
                startActivity(intent)
            }

            bind.other.setOnClickListener {
                val intent= Intent(this, View_page::class.java)
                intent.putExtra("cat","other")
                intent.putExtra("id",id)
                startActivity(intent)
            }
        }



    }
}