package com.example.taller_movil.network

import kotlinx.serialization.Serializable

@Serializable
data class UsersResponse(val users: List<User>)

@Serializable
data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val image: String,
    val phone: String,
    val email: String? = null,
    val age: Int? = null,
    val gender: String? = null,
    val birthDate: String? = null,
    val company: Company? = null,
    val address: Address? = null
)

@Serializable
data class Company(
    val name: String? = null,
    val department: String? = null,
    val title: String? = null
)

@Serializable
data class Address(
    val address: String? = null,
    val city: String? = null,
    val state: String? = null
)
