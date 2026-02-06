package com.example.collegeeventmanager

import com.google.firebase.Timestamp
import java.time.Instant

data class PostData(val caption:String="",
                   val fileId:String="",
                   val imageUrl:String="",
                   val publicId:String="",
                    val userId:String="",
                    val createdAt: Timestamp?=null,
                  val category:String=""
             ){

}
