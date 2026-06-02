package com.example.testkmpdecomposeapp.feature.c.impl

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import org.koin.dsl.module
import kotlin.random.Random

private const val FEATURE_C_CHANNEL_ID = "feature_c_channel"

actual val featureCPlatformModule = module {
    single<FeatureCNotificationManager> {
        AndroidFeatureCNotificationManager(context = get())
    }
}

private class AndroidFeatureCNotificationManager(
    private val context: Context
) : FeatureCNotificationManager {

    override fun showTestNotification(itemId: Int) {
        ensureNotificationChannel()
        if (!canPostNotifications()) return

        val notification = NotificationCompat.Builder(context, FEATURE_C_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Feature C")
            .setContentText("Test notification for item #$itemId")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(Random.nextInt(), notification)
    }

    private fun ensureNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val manager = context.getSystemService(NotificationManager::class.java) ?: return
        val channel = NotificationChannel(
            FEATURE_C_CHANNEL_ID,
            "Feature C notifications",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Test notifications for Feature C"
        }
        manager.createNotificationChannel(channel)
    }

    private fun canPostNotifications(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return true

        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    }
}
