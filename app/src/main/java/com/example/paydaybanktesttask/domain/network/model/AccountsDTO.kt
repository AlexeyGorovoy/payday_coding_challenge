package com.example.paydaybanktesttask.domain.network.model

import com.example.paydaybanktesttask.domain.repository.model.Account
import com.example.paydaybanktesttask.util.fromLongString
import com.google.gson.annotations.SerializedName
import java.util.*

data class AccountsDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("customer_id") val customerId: Long,
    @SerializedName("iban") val iban: String,
    @SerializedName("type") val type: String,
    @SerializedName("date_created") val dateCreated: String,
    @SerializedName("active") val active: Boolean

) : Dto<Account> {

    override fun convert(): Account {
        return Account(
            id = id,
            customerId = customerId,
            iban = iban,
            type = type,
            dateCreated = dateCreated.fromLongString() ?: Date(),
            active = active

        )
    }
}
