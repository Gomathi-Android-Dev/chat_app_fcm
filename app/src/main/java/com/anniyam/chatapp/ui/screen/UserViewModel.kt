package com.anniyam.chatapp.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.anniyam.chatapp.data.model.User
import com.anniyam.chatapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel  @Inject constructor(
    private val repo: UserRepository
) : ViewModel() {

    var userList by mutableStateOf<List<User>>(emptyList())
        private set

    fun loadUsers() {
        repo.getUsers {
            userList = it
        }
    }
}