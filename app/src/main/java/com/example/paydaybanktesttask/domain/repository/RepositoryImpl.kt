package com.example.paydaybanktesttask.domain.repository

import com.example.paydaybanktesttask.domain.network.api.Api
import com.example.paydaybanktesttask.domain.repository.model.Account
import com.example.paydaybanktesttask.domain.network.model.AuthenticateBody
import com.example.paydaybanktesttask.domain.network.model.CustomerBody
import com.example.paydaybanktesttask.domain.repository.model.Customer
import com.example.paydaybanktesttask.domain.repository.model.Transaction
import io.reactivex.Single
import retrofit2.Retrofit

class RepositoryImpl(private val retrofit: Retrofit) : Repository {

    private val api by lazy { retrofit.create(Api::class.java) }

    override fun getAccounts(): Single<List<Account>> {
        return api.accounts().map { list -> list.map { it.convert() } }
    }

    override fun getTransactions(): Single<List<Transaction>> {
        return api.transactions().map { list -> list.map { it.convert() } }
    }

    override fun postAuthenticate(body: AuthenticateBody): Single<Customer> {
        return api.authenticate(body).map { it.convert() }
    }

    override fun postCustomers(body: CustomerBody): Single<Customer> {
        return api.customers(body).map { it.convert() }
    }


}