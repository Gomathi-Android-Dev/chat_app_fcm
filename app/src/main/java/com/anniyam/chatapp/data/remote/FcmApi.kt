package com.anniyam.chatapp.data.remote

import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface FcmApi {
    @Headers(
        "Content-Type: application/json",
        "Authorization: key=YOUR_SERVER_KEY_FROM_FIREBASE_CONSOLE"
    )
    @POST("fcm/send")
    suspend fun sendNotification(@Body payload: NotificationPayload): String

}
data class NotificationPayload(val to: String, val notification: NotificationData)
data class NotificationData(val title: String, val body: String)