package com.example.paydaybanktesttask.ui.information.transaction

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.paydaybanktesttask.core.SingleEvent
import com.example.paydaybanktesttask.domain.repository.Repository
import com.example.paydaybanktesttask.domain.repository.model.Transaction
import com.example.paydaybanktesttask.ui.BaseViewModel
import com.example.paydaybanktesttask.ui.information.InformationActivity.Companion.FAKE_DELAY_MSEC
import com.example.paydaybanktesttask.util.convertAccountsToId
import com.example.paydaybanktesttask.util.fromIoToMain
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class TransactionsViewModel(val repository: Repository, val appContext: Context) : BaseViewModel(appContext) {

    private val _eventGetTransactionList = MutableLiveData<SingleEvent<List<Transaction>>>()
    val eventGetTransactionList: LiveData<SingleEvent<List<Transaction>>>
        get() = _eventGetTransactionList

    @SuppressLint("CheckResult")
    fun callTransactionsListGetting(clientId: Long) {
        repository.getAccounts()
            .delay(FAKE_DELAY_MSEC, TimeUnit.MILLISECONDS)
            .map { it.filter { account -> account.customerId == clientId } }
            .flatMap { it.convertAccountsToId() }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .withProgress()
            .subscribe({ idAccountsList ->
                repository.getTransactions()
                    .map {
                        it.filter { transaction -> idAccountsList.contains(transaction.accountId) }
                    }.fromIoToMain()
                    .subscribe(
                        { result ->
                            _eventGetTransactionList.value = SingleEvent(result)
                        },
                        { error ->
                            fireMessage(error.message)
                        }
                    )
            }, { error ->
                fireMessage(error.message)
            }).unsubscribeOnClear()
    }
}