package com.anniyam.chatapp.ui.screen

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.activity.result.launch
import androidx.compose.foundation.layout.add
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anniyam.chatapp.R
import com.anniyam.chatapp.data.remote.FcmApi
import com.anniyam.chatapp.data.remote.NotificationData
import com.anniyam.chatapp.data.remote.NotificationPayload
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseDatabase,
    @ApplicationContext private val context: Context // 1. Inject Context here
) : ViewModel() {
    fun showNotification(title: String, message: String) {
        // 2. Use context.getSystemService
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = "chat_channel"

        // 3. Create Notification Channel (Required for Android 8.0+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Chat Messages",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        // 4. Pass context to the Builder instead of 'this'
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
    var messages = mutableStateListOf<com.anniyam.chatapp.data.model.Message>()
        private set
     private var chatListener: ValueEventListener? = null

    fun listenForMessages(receiverId: String) {
        val senderId = auth.currentUser?.uid ?: return
        // Create a unique chat ID by sorting UIDs (ensures both users share the same node)
        val chatId = if (senderId < receiverId) "${senderId}_$receiverId" else "${receiverId}_$senderId"

        chatListener = db.reference.child("chats").child(chatId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messages.clear()
                    for (postSnapshot in snapshot.children) {
                        val message = postSnapshot.getValue(com.anniyam.chatapp.data.model.Message::class.java)
                        message?.let { messages.add(it) }
                    }
                }
                override fun onCancelled(error: DatabaseError) {}
            })
    }
    private val fcmApi = Retrofit.Builder()
        .baseUrl("https://fcm.googleapis.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(FcmApi::class.java)

    fun sendMessage(receiverId: String, text: String) {
        val senderId = auth.currentUser?.uid ?: return
        val chatId = if (senderId < receiverId) "${senderId}_$receiverId" else "${receiverId}_$senderId"

        val timestamp = java.text.SimpleDateFormat("hh:mm a", java.util.Locale.getDefault()).format(java.util.Date())

        val message = com.anniyam.chatapp.data.model.Message(
            text = text,
            isMine = true, // We will calculate this based on senderId in the UI bubble
            time = timestamp,
            userId = senderId // Add this field to your Message model
        )

        db.reference.child("chats").child(chatId).push().setValue(message)
            .addOnSuccessListener {
                // 2. Fetch Receiver's Token and Send Notification
                sendPushNotification(receiverId, "Hey beautifull u got a msg", text)
            }
    }
    private fun sendPushNotification(receiverId: String, title: String, message: String) {
        Log.e("TAG", "sendPushNotification: $receiverId $title $message")
        db.reference.child("users").child(receiverId).child("fcmToken").get()
            .addOnSuccessListener { snapshot ->
                val token = snapshot.getValue(String::class.java)
                Log.e("TAG", "sendPushNotification: $token")
                if (token != null) {
                    viewModelScope.launch {
                        try {
                            val payload = NotificationPayload(
                                to = token,
                                notification = NotificationData(title, message)
                            )
                            //fcmApi.sendNotification(payload)
                            showNotification(title,message)  //local notification
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
    }



    override fun onCleared() {
        chatListener?.let { /* Remove listener logic */ }
        super.onCleared()
    }


}