package com.ccino.demo.sdk

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.IconCompat
import com.ccino.demo.R
import com.ccino.demo.databinding.ActivitySecondBinding
import com.ccino.demo.view.WidgetActivity


class NotifyActivity : ComponentActivity() {
    private lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dropTextView = binding.btn
        dropTextView.setOnClickListener {
            sendNotification()
        }
    }

    private fun sendNotification() {
        val requestCode = 0
        val intent = Intent()
        intent.setClassName(packageName, WidgetActivity::class.java.name)

        val pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_IMMUTABLE)
        val channelId = "default"

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(IconCompat.createWithBitmap(BitmapFactory.decodeResource(resources, R.drawable.push_app_logo)))
            .setContentTitle("msg.notification?.title")
            .setContentText("msg.notification?.body")
            .setAutoCancel(true)
            .setLargeIcon(IconCompat.createWithResource(this, R.drawable.push_app_logo).toIcon())
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "channel_name", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(11, notificationBuilder.build())
    }
}