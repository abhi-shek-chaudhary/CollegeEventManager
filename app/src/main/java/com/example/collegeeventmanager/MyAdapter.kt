package com.example.collegeeventmanager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.ui.layout.Layout

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
       val image=view.findViewById<ImageView>(R.id.image)
        userName.text=user[position].userId
        caption.text=user[position].caption
        Glide.with(context)
            .load(user[position].imageUrl)
            .into(image)
        return view

    }


}


