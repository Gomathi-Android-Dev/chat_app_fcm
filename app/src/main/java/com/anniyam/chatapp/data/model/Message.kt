package com.anniyam.chatapp.data.model

data class Message(
    val text: String = "",
    val isMine: Boolean = false,
    val time: String = "",
    val userId: String = "",
    val userName: String = "",
)
