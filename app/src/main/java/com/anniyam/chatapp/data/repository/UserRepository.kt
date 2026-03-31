package com.anniyam.chatapp.data.repository

import com.anniyam.chatapp.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor() {
    private val db = FirebaseDatabase.getInstance().reference
    val currentUid = FirebaseAuth.getInstance().currentUser?.uid

    fun getUsers(onResult: (List<User>) -> Unit) {
        db.child("users")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val userList = mutableListOf<User>()

                    for (userSnap in snapshot.children) {
                        val user = userSnap.getValue(User::class.java)

                        user?.let {
                            val uid = userSnap.key ?: ""

                            // ✅ Skip current logged-in user
                            if (uid != currentUid) {
                                userList.add(it.copy(uid = uid))
                            }
                        }
                    }

                    onResult(userList)
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }
}