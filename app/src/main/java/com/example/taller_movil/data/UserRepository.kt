package com.example.taller_movil.data

import com.example.taller_movil.network.User
import com.example.taller_movil.network.UserApiService

class UserRepository(
    private val api: UserApiService = UserApiService()
) {
    private var cache: List<User>? = null

    suspend fun users(): List<User> {
        if (cache == null) cache = api.getUsers()
        return cache!!
    }

    fun findById(id: Int): User? = cache?.firstOrNull { it.id == id }
}
