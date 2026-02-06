package com.example.collegeeventmanager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.ui.layout.Layout
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
// Cloudinary and Glide Imports
import com.bumptech.glide.Glide
import com.cloudinary.android.MediaManager
import com.cloudinary.Url
import com.cloudinary.Transformation



// Assuming PostData is a data class defined elsewhere:
// data class PostData(val publicId: String, val caption: String, val userName: String)

class MyAdapter(context: Context, val user: ArrayList<PostData>):
    ArrayAdapter<PostData>(context,R.layout.viewid,user) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
      val inflt= LayoutInflater.from(context)
        val view=inflt.inflate(R.layout.viewid,null)
        val userName=view.findViewById<TextView>(R.id.userName)
        val caption=view.findViewById<TextView>(R.id.caption)
        val date=view.findViewById<TextView>(R.id.date)
       val image=view.findViewById<ImageView>(R.id.image)
        userName.text=user[position].userId
        caption.text=user[position].caption
        date.text=formatTimestamp(user[position].createdAt)
        Glide.with(context)
            .load(user[position].imageUrl)
            .into(image)
        return view

    }
    fun formatTimestamp(timestamp: Timestamp?): String {
        if (timestamp == null) {
            return "Unknown Date"
        }

        val date: Date = timestamp.toDate()

        val formatter = SimpleDateFormat("MMM d, yyyy 'at' h:mm a", Locale.getDefault())

        return formatter.format(date)
    }


}


