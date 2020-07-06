package com.example.paydaybanktesttask.domain.repository.model

import java.util.*

data class Transaction(
    val id: Long,
    val accountId: Long,
    val amount: Double,
    val vendor: String,
    val category: String,
    val date: Date

)