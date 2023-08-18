package com.example.notificationlistenerservice

import android.content.Intent
import android.content.pm.PackageManager
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import androidx.core.app.NotificationCompat

class MyNotificationListenerService : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        super.onNotificationPosted(sbn)
        var appName = "Unknown"
        try {
            val packageName = sbn.packageName
            val pm = packageManager
            val appInfo = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            appName = pm.getApplicationLabel(appInfo).toString()
        }catch (e: PackageManager.NameNotFoundException) {
            // Handle the exception, for example:
            e.printStackTrace()
        }


        val notification = sbn.notification
        val title = notification.extras.getString(NotificationCompat.EXTRA_TITLE)
        val text = notification.extras.getCharSequence(NotificationCompat.EXTRA_TEXT)

        val intent = Intent("update-notification")
        intent.putExtra("appName", appName)
        intent.putExtra("title", title)
        intent.putExtra("content", text.toString())
        sendBroadcast(intent)
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        super.onNotificationRemoved(sbn)
        // Notification removed
    }
}
