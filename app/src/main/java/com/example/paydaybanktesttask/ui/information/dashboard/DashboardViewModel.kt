package com.example.paydaybanktesttask.ui.information.dashboard

import android.annotation.SuppressLint
import android.content.Context

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.paydaybanktesttask.R
import com.example.paydaybanktesttask.domain.repository.Repository
import com.example.paydaybanktesttask.domain.repository.model.Dashboard
import com.example.paydaybanktesttask.domain.repository.model.Transaction
import com.example.paydaybanktesttask.ui.BaseViewModel
import com.example.paydaybanktesttask.ui.information.InformationActivity.Companion.FAKE_DELAY_MSEC
import com.example.paydaybanktesttask.util.convertAccountsToId
import com.example.paydaybanktesttask.util.fromIoToMain
import io.reactivex.schedulers.Schedulers
import java.text.DateFormatSymbols
import java.util.*
import java.util.concurrent.TimeUnit

class DashboardViewModel(val repository: Repository, val appContext: Context) :
    BaseViewModel(appContext) {

    private val eventGetDashboardList = MutableLiveData<List<Dashboard>>()
    val getEventGetDashboardList: LiveData<List<Dashboard>> = eventGetDashboardList

    lateinit var listFull: MutableList<Transaction>

    @SuppressLint("CheckResult")
    fun callDashboardListPreparing(clientId: Long) {
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
                        it.filter { transaction ->
                            idAccountsList.contains(transaction.accountId)
                        }
                    }
                    .fromIoToMain()
                    .subscribe(
                        { result ->
                            listFullSet(result)
                            updateDashboard(prepareDashboardItemList(listFull))
                        },
                        { error -> fireMessage(error.message) }
                    )
            }, { error ->
                fireMessage(error.message)
            })
            .unsubscribeOnClear()
    }

    fun callDashboardFilteringByPeriod(periodMonths: Int) {
        var list = mutableListOf<Transaction>()
        if (this::listFull.isInitialized)
            if (periodMonths != 0) {
                val date = prepareSelectedDate(periodMonths)
                for (transaction in listFull) {
                    if (transaction.date.after(date)) {
                        list.add(transaction)
                    }
                }
            } else {
                list = listFull
            }
        updateDashboard(prepareDashboardItemList(list))
    }

    fun listFullSet(list: List<Transaction>) {
        listFull = mutableListOf()
        listFull?.let {
            listFull.clear()
            list.map { listFull.add(it) }
        }
    }

    private fun prepareSelectedDate(period: Int): Date {
        return Calendar.getInstance()
            .apply { add(Calendar.MONTH, -period) }
            .time
    }

    private fun prepareDashboardItemList(list: List<Transaction>): List<Dashboard> {
        return list.groupBy { transaction -> transaction.category }.map { categoryMapEntry ->
            val sBuilder = StringBuilder()
            categoryMapEntry.value.groupBy { categoryTransaction ->
                categoryTransaction.date.month
            }.map { monthCategoryTransactions ->
                sBuilder
                    .append(
                        "${appContext.resources.getString(R.string.dashboard_item_description_at_month)} "
                    )
                    .append(
                        "${getMonthByKey(monthCategoryTransactions.key)} ${appContext.resources.getString(
                            R.string.dashboard_item_description_amount
                        )} "
                    )
                    .append(
                        "${monthCategoryTransactions.value.foldRight(
                            0.00,
                            { v1, v2 -> v1.amount + v2 })}"
                    )
                    .append("\n")
            }

            Dashboard(
                categoryMapEntry.key,
                sBuilder.toString().dropLast(1)
            )
        }
    }

    private fun getMonthByKey(key: Int) = DateFormatSymbols().months[key - 1]

    private fun updateDashboard(dashboardItemList: List<Dashboard>) {
        eventGetDashboardList.value = dashboardItemList
    }
}