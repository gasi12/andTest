package com.example.andtest.api.downloader

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import com.example.andtest.SecurePreferences

class AndroidDownloader(
    private val context: Context
): Downloader {
    val securePreferences = SecurePreferences
        .getInstance(context)
    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    override fun downloadFile(url: String,serviceId: Long): Long {
        val request = DownloadManager.Request(url.toUri())
            .setMimeType("application/pdf")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle("service $serviceId.pdf")
            .addRequestHeader("Authorization", "Bearer ".plus(securePreferences.getToken(SecurePreferences.TokenType.AUTH)))
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "service $serviceId.pdf")
        return downloadManager.enqueue(request)
    }
}