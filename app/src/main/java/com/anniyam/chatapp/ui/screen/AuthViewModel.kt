package com.anniyam.chatapp.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    val db = FirebaseDatabase.getInstance().reference

    var isLoginSuccessful by mutableStateOf(false)
        private set
    var loginState by mutableStateOf<String?>(null)
        private set

    fun login(email: String, password: String,userName: String) {
        if (email.isBlank() || password.isBlank()) {
            loginState = "Email and Password required"
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid ?: return@addOnCompleteListener

                    val user = mapOf(
                        "name" to  userName,
                        "email" to email
                    )

                    db.child("users")
                        .child(uid)
                        .setValue(user)
                        .addOnSuccessListener {
                        loginState = "Success"
                        isLoginSuccessful = true // 👈 Set flag to true
                    }

                    loginState = "Success"

                } else {
                    loginState = task.exception?.message ?: "Login Failed"
                }
            }
    }
    fun onNavigated() {
        isLoginSuccessful = false
    }
}