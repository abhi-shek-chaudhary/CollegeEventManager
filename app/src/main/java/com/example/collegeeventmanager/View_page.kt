package com.example.collegeeventmanager

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.rpc.ErrorInfo
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class View_page : AppCompatActivity() {

    private val LOG_TAG = "ViewPageActivity"
    private val lis = arrayListOf<PostData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_page)
        lis.clear()
        lifecycleScope.launch {
            fetchAllPosts()
        }
    }

    private suspend fun fetchAllPosts() {
        val db = Firebase.firestore


        val postsCollectionRef = db.collection("Post")
        try{
            val ref1=postsCollectionRef.get().await()
            for(document in ref1){
                val post=postsCollectionRef.document(document.id).collection("PostId")
                val postref=post.get().await()
                for(doc in postref){
                    val data=doc.toObject<PostData>()
                    lis.add(data)

                }
            }


        }catch (e: Exception){

        }

        setupListView(lis)

    }

    private fun setupListView(dataList: ArrayList<PostData>) {
        val view = findViewById<ListView>(R.id.listView)
        view.adapter = MyAdapter(this, dataList)
    }
}