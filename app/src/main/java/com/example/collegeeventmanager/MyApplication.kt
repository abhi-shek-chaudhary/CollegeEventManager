package com.example.collegeeventmanager

import android.app.Application
import com.cloudinary.android.MediaManager
import java.util.HashMap

/**
 * Custom Application class used for global, one-time initialization of third-party libraries
 * like Cloudinary, before any Activity is launched.
 * * IMPORTANT: This class must be registered in the AndroidManifest.xml file
 * using the 'android:name=".MyApplication"' attribute under the <application> tag.
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupCloudinary()
    }

    /**
     * Initializes the Cloudinary MediaManager with the necessary configuration details.
     */
    private fun setupCloudinary() {
        // Map to hold configuration parameters
        val config: MutableMap<String, String> = HashMap()

        // !!! IMPORTANT: Replace these placeholders with your actual Cloudinary credentials !!!
        // You get these from your Cloudinary dashboard.
        config["cloud_name"] = "dbgjafkst"
        config["api_key"] = "355645536256193"

        // WARNING: Storing your API Secret directly in client code (Android/iOS) is INSECURE.
        // This is only safe for testing. For production, use Unsigned Uploads or a secure backend.
        config["api_secret"] = "kU_BLvYhE4ebs1bHwmkrDZm0VLA"

        try {
            // Initialize the MediaManager with the global context and configuration
            MediaManager.init(this, config)
            println("Cloudinary initialized successfully.")
        } catch (e: Exception) {
            // Handle initialization failure (e.g., missing dependencies or bad config)
            println("Cloudinary initialization failed: ${e.message}")
            // Consider logging this failure to Firebase Crashlytics or a similar tool
        }
    }
}