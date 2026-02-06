package com.example.collegeeventmanager

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo


// 2. Interface defining the upload callbacks (onSuccess, onError, etc.)
import com.cloudinary.android.callback.UploadCallback
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore


class posting : AppCompatActivity() {
    companion object{
        lateinit var caption: String
    }

private var selectedImageUri: Uri?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_posting)
        val choose=findViewById<Button>(R.id.upload)
        val id=intent.getStringExtra("id")
        val category=intent.getStringExtra("cat")

        choose.setOnClickListener {
            val intent= Intent(Intent.ACTION_GET_CONTENT)
            intent.type="image/*"
            imagePickerLauncher.launch(intent)
        }

        val save=findViewById<Button>(R.id.button)
        save.setOnClickListener {
            val etcaption=findViewById<TextInputEditText>(R.id.caption)
            caption=etcaption.text.toString().trim()
               uploadImageToCloudinary(selectedImageUri!!,id!!,category!!)
        }

    }
    private fun uploadImageToCloudinary(imageUri: Uri, userId: String,category: String) {

        Toast.makeText(this, "Starting image upload...", Toast.LENGTH_LONG).show()
        // Disable during upload

        MediaManager.get()
            .upload(imageUri)
            .option("resource_type", "image")
            .option("folder", "college_events/$userId")
            .callback(object : UploadCallback {

                override fun onStart(requestId: String) {
                    println("Upload $requestId started.")
                    // Show progress UI element
                }

                override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {
                    val progress = (bytes * 100 / totalBytes).toInt()
                    println("Upload $requestId progress: $progress%")
                }

                override fun onSuccess(requestId: String, resultData: Map<*, *>) {

                    val imageUrl = resultData["secure_url"] as? String

                    // The raw public ID includes the folder: "college_events/Admin/umhtuxlpkuiib1dfnexq"
                    val rawPublicId = resultData["public_id"] as? String

                    // --- 1. FIX: Extract only the unique file ID ---
                    // This finds the last segment, which should be "umhtuxlpkuiib1dfnexq"
                    val fileId: String? = rawPublicId?.substringAfterLast('/')

                    // Safety checks
                    if (imageUrl == null || fileId == null) {
                        println("Cloudinary returned success but missing URL or file ID.")
                        return
                    }


                    val db = Firebase.firestore

                    // 2. Prepare the Firestore data
                    val event = hashMapOf(
                        "imageUrl" to imageUrl,
                        "userId" to userId,
                        "publicId" to rawPublicId, // <-- Store the FULL ID for Cloudinary API reference later
                        "fileId" to fileId,        // <-- Store the simple ID for simpler referencing
                        "caption" to caption,
                        "createdAt" to FieldValue.serverTimestamp(),
                        "category" to category
                    )

                    // 3. Save to Firestore using the CLEANED fileId
                    val postref = db.collection("Post").document(userId)
                    val postIdRef = postref.collection("PostId")

                    // --- TRANSACTION START ---
                    // Perform both operations within a runTransaction block for safety if possible,
                    // but for simple checks/updates like this, a standard chain is also fine.

                    // 2. Guarantee the parent document exists (Efficiently writes ONLY the parent document).
                    // SetOptions.merge() only writes the 'name' field and won't overwrite other fields if the document exists.
                    postref.set(
                        mapOf("name" to userId),
                        com.google.firebase.firestore.SetOptions.merge()
                    )
                        .addOnSuccessListener {
                            // 3. Check for the specific batch document's existence.
                            postIdRef.document(fileId).get()
                                .addOnSuccessListener { batchSnapshot ->
                                    if (batchSnapshot.exists()) {
                                        // Batch exists: Increment the quantity.
                                        postIdRef.document(fileId)
                                            .update("fileId", fileId)

                                    } else {
                                        // New batch: Set the full batch data.
                                        postIdRef.document(fileId).set(event)

                                    }

                                }
                        }
                }

                override fun onError(requestId: String, error: ErrorInfo) {

                    Toast.makeText(this@posting, "Upload Failed: ${error.description}", Toast.LENGTH_LONG).show()
                    println("Cloudinary Upload Error: ${error.description}")
                }

                override fun onReschedule(requestId: String, error: ErrorInfo) {}
            })
            .dispatch()
    }

    private val imagePickerLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            // Check if the operation was successful (user selected an image)
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                // The URI points to the selected image file on the device
                selectedImageUri = data?.data

                // Handle the successful URI here...
                if (selectedImageUri != null) {
                    // ... logic to show preview and enable upload button
                    Toast.makeText(this,"Image uploaded successfully", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this,"Please Select Image", Toast.LENGTH_SHORT).show()
                }
            }
        }
}