package com.example.notificationlistenerservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.DateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var notificationRecyclerView: RecyclerView
    private lateinit var notificationAdapter: NotificationAdapter
    private val notifications = mutableListOf<Notification>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationRecyclerView = findViewById(R.id.notification_list)
        notificationAdapter = NotificationAdapter(this,notifications)
        notificationRecyclerView.adapter = notificationAdapter
        notificationRecyclerView.layoutManager = LinearLayoutManager(this)

        val filter = IntentFilter("update-notification")
        registerReceiver(updateReceiver, filter)

        val serviceIntent = Intent(applicationContext, MyNotificationListenerService::class.java)
        startService(serviceIntent)
        notifications.clear()
    }
    private val updateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                // to get current time and Date
                val calender = Calendar.getInstance().time
                val dateFormat = DateFormat.getDateInstance(DateFormat.FULL).format(calender)
                val timeFormat = DateFormat.getTimeInstance().format(calender)

                val title = it.getStringExtra("title")
                val appName = it.getStringExtra("appName")
                val content = it.getStringExtra("content")
                addNotification(Notification(appName,content,timeFormat,title))
            }
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(updateReceiver)
    }

    fun addNotification(notification: Notification) {
        notifications.add(notification)
        notifications.sortByDescending { it.timestamp } // Sort in descending order
        notificationAdapter.submitList(notifications)
        notificationAdapter.notifyDataSetChanged() // Notify adapter of data change
    }
}
