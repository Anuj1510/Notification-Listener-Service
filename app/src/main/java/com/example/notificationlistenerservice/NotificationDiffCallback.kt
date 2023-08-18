package com.example.notificationlistenerservice

import androidx.recyclerview.widget.DiffUtil

class NotificationDiffCallback : DiffUtil.ItemCallback<Notification>() {
    override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
        return oldItem.timestamp == newItem.timestamp // Change this to a unique identifier for your items
    }

    override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
        return oldItem == newItem // Assuming your Notification class has appropriate equals() implementation
    }
}