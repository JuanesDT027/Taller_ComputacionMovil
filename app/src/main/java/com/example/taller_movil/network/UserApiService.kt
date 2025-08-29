package com.example.taller_movil.network

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class UserApiService(private val client: ApiClient = ApiClient) {

    suspend fun getUsers(): List<User> {
        val resp: UsersResponse = client.http.get {
            url {
                takeFrom(ApiClient.BASE_URL)
                encodedPath = "/users" // retorna un objeto con "users": [...]
            }
            accept(ContentType.Application.Json)
        }.body()
        return resp.users
    }
}
