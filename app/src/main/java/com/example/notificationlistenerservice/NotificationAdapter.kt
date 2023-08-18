package com.example.notificationlistenerservice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class NotificationAdapter(
    val context: Context,
    private val notificationList: MutableList<Notification>
) : ListAdapter<Notification, NotificationAdapter.ViewHolder>(diffCallback) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val appName = itemView.findViewById<TextView>(R.id.App_name)
        val textTime = itemView.findViewById<TextView>(R.id.Text_Time)
        val textDetails = itemView.findViewById<TextView>(R.id.Text_Detail)
        val textTitle = itemView.findViewById<TextView>(R.id.Text_Tittle)
    }

    // Implement DiffUtil.ItemCallback for comparing items
    object diffCallback : DiffUtil.ItemCallback<Notification>() {
        override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
            return oldItem.timestamp == newItem.timestamp // Change to your unique identifier
        }

        override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.list_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentNotification = getItem(position)

        holder.appName.text = currentNotification.appName
        holder.textTime.text = currentNotification.timestamp
        holder.textDetails.text = currentNotification.content
        holder.textTitle.text = currentNotification.tittle
    }

    // Function to submit a new list to the adapter
    fun submitNotificationList(newList: List<Notification>) {
        submitList(newList)
    }
}
