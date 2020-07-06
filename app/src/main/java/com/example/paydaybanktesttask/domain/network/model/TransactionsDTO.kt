package com.example.paydaybanktesttask.domain.network.model

import com.example.paydaybanktesttask.domain.repository.model.Transaction
import com.example.paydaybanktesttask.util.fromLongString
import com.google.gson.annotations.SerializedName
import java.lang.NumberFormatException
import java.util.*

data class TransactionsDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("account_id") val accountId: Long,
    @SerializedName("amount") val amount: String,
    @SerializedName("vendor") val vendor: String,
    @SerializedName("category") val category: String,
    @SerializedName("date") val date: String

) : Dto<Transaction> {
    override fun convert(): Transaction {
        return Transaction(
            id = id,
            accountId = accountId,
            amount = try {amount.toDouble() } catch (e:NumberFormatException) { 0.0 },
            vendor = vendor,
            category = category,
            date = date.fromLongString() ?: Date()
        )
    }
}