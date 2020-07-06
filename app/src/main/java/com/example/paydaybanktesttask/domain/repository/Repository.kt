package com.example.paydaybanktesttask.domain.repository

import com.example.paydaybanktesttask.domain.repository.model.Account
import com.example.paydaybanktesttask.domain.network.model.AuthenticateBody
import com.example.paydaybanktesttask.domain.network.model.CustomerBody
import com.example.paydaybanktesttask.domain.repository.model.Customer
import com.example.paydaybanktesttask.domain.repository.model.Transaction
import io.reactivex.Single

interface Repository {

    fun getAccounts(): Single<List<Account>>
    fun getTransactions(): Single<List<Transaction>>
    fun postAuthenticate(body: AuthenticateBody): Single<Customer>
    fun postCustomers(body: CustomerBody): Single<Customer>
}