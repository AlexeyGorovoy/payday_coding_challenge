package com.example.paydaybanktesttask.domain.repository.model

data class Customer(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val email: String,
    val password: String,
    val dob: String,
    val phone: String
)