package com.example.toyproject009_pushalarm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        createNotificationChannel()

        val type = remoteMessage.data["type"]
            ?.let { NotificationType.valueOf(it) }
        val title = remoteMessage.data["title"]
        val message = remoteMessage.data["message"]

        type ?: return


        NotificationManagerCompat.from(this)
            .notify(type.id, createNotification(type, title, message))
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = CHANNEL_DESCRIPTION

            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
        }
    }

    private fun createNotification(
        type: NotificationType,
        title: String?,
        message: String?
    ): Notification {
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        when (type) {
            NotificationType.NORMAL -> Unit
            NotificationType.EXPANDABLE -> {
                notificationBuilder.setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(
                            "\uD83D\uDEA6\n" +
                                    "\uD83C\uDF10\n" +
                                    "\uD83C\uDF0E\n" +
                                    "\uD83C\uDF78\n" +
                                    "\uD83D\uDDFE\n" +
                                    "\uD83C\uDF74\n" +
                                    "\uD83D\uDC22\n" +
                                    "\uD83D\uDC0D\n" +
                                    "\uD83E\uDD9F\n" +
                                    "\uD83E\uDD32\n" +
                                    "\uD83D\uDC4F\n" +
                                    "\uD83E\uDDB4\n" +
                                    "\uD83E\uDEC0\n" +
                                    "\uD83D\uDC81\n" +
                                    "\uD83D\uDE4D\n" +
                                    "\uD83E\uDD8D\n" +
                                    "\uD83E\uDD3C\n" +
                                    "\uD83D\uDC7B\n" +
                                    "\uD83D\uDC7D\n" +
                                    "\uD83D\uDC7E\n" +
                                    "\uD83E\uDD16\n" +
                                    "\uD83D\uDE3A\n" +
                                    "\uD83D\uDE22\n" +
                                    "\uD83D\uDE25\n" +
                                    "\uD83E\uDD74\n" +
                                    "\uD83D\uDE02\n" +
                                    "\uD83E\uDD23\n" +
                                    "\uD83D\uDE05\n" +
                                    "\uD83D\uDE01\n" +
                                    "\uD83D\uDE03\n" +
                                    "\uD83D\uDE00"
                        )
                )
            }
            NotificationType.CUSTOM -> {
                notificationBuilder.setStyle(
                    NotificationCompat.DecoratedCustomViewStyle()
                )
                    .setCustomContentView(
                        RemoteViews(
                            packageName,
                            R.layout.view_coustom_nofitication
                        ).apply {
                            setTextViewText(R.id.title, title)
                            setTextViewText(R.id.message, message)
                        }
                    )

            }
        }

        return notificationBuilder.build()
    }

    companion object {
        private const val CHANNEL_NAME = "Emoji party"
        private const val CHANNEL_DESCRIPTION = "Emoji party를 위한 채널"
        private const val CHANNEL_ID = "Channel ID"
    }
}