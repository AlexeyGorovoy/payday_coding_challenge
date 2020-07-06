package com.example.paydaybanktesttask.util

import com.example.paydaybanktesttask.domain.repository.model.Account
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.fromIoToMain(): Single<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun List<Account>.convertAccountsToId(): Single<List<Long>> {
    val tempList = mutableListOf<Long>()
    this.map { tempList.add(it.id) }
    return Single.create { it.onSuccess(tempList) }
}
