package com.example.paydaybanktesttask.domain.network.model

import com.google.gson.annotations.SerializedName

data class AuthenticateBody(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)