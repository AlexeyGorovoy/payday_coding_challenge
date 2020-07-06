package com.example.paydaybanktesttask.domain.repository.model

import java.util.*

data class Account(
    val id: Long,
    val customerId: Long,
    val iban: String,
    val type: String,
    val dateCreated: Date,
    val active: Boolean
)