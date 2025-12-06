package com.example.collegeeventmanager

import android.app.Application
import com.cloudinary.android.MediaManager
import com.example.collegeeventmanager.BuildConfig
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

        // 2. Fetch the secrets from the generated BuildConfig class!
        config["cloud_name"] = BuildConfig.CLOUDINARY_CLOUD_NAME
        config["api_key"] = BuildConfig.CLOUDINARY_API_KEY

        // WARNING: See note below regarding API Secret safety.
        config["api_secret"] = BuildConfig.CLOUDINARY_API_SECRET

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