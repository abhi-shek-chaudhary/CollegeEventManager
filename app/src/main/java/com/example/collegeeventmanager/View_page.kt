package com.example.collegeeventmanager

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
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
        val cat=intent.getStringExtra("cat")
        val id=intent.getStringExtra("id")
        lifecycleScope.launch {
            fetchAllPosts(cat)
        }
    }

    private suspend fun fetchAllPosts(cat: String?) {
        val db = Firebase.firestore

        val collectionGroupRef = db.collectionGroup("PostId")
        lis.clear()

        try {
            val querySnapshot = collectionGroupRef
                .orderBy("createdAt", Query.Direction.DESCENDING) // Critical sorting step
                .get()
                .await()

            // 3. Process the results from the CORRECT query
            for (document in querySnapshot.documents) {
                // toObject() converts the Firestore document into your PostData object
                val data = document.toObject<PostData>()
                if (data != null) {
                    if(data.category==cat) {
                        lis.add(data)
                    }
                } else {
                    Log.w(LOG_TAG, "Document failed to convert to PostData: ${document.id}")
                }
            }

            // 4. Call setupListView ONLY after the successful fetch and population
            setupListView(lis)

        } catch (e: Exception) {
            // Log the error instead of leaving the block empty
            Log.e(LOG_TAG, "Error fetching posts from Firestore:", e)
            // Optional: Show a Toast to the user here
        }




        setupListView(lis)

    }

    private fun setupListView(dataList: ArrayList<PostData>) {
        val view = findViewById<ListView>(R.id.listView)
        view.adapter = MyAdapter(this, dataList)
    }
}