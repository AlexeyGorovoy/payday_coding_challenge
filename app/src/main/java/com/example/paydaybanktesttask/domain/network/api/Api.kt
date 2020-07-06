package com.example.paydaybanktesttask.domain.network.api

import com.example.paydaybanktesttask.domain.network.model.*
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
interface Api {

    @GET("/accounts")
    fun accounts(): Single<List<AccountsDTO>>

    @GET("/transactions")
    fun transactions(): Single<List<TransactionsDTO>>

    @POST("/authenticate")
    fun authenticate(@Body body: AuthenticateBody): Single<CustomerDTO>

    @POST("/customers")
    fun customers(@Body body: CustomerBody): Single<CustomerDTO>
}