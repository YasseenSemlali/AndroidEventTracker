package com.finleystewart.eventfinleyyasseen.firebase.model

import com.finleystewart.eventfinleyyasseen.business.User

data class DBUser(
    val email: String = "",
    val favourited: List<String> = listOf()
) {
    fun toUser(): User {
        return User(email, favourited)
    }
}