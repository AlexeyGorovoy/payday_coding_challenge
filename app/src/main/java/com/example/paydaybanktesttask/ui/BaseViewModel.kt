package com.example.paydaybanktesttask.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.paydaybanktesttask.core.SingleEvent
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel(private val appContext: Context) : ViewModel() {

    val toastMessageLiveData: LiveData<SingleEvent<String>>
        get() = _toastMessageLiveData

    private val _toastMessageLiveData = MutableLiveData<SingleEvent<String>>()

    val showProgressLiveData: LiveData<Boolean>
        get() = _showProgressLiveData

    private val _showProgressLiveData = MutableLiveData<Boolean>()

    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    protected fun Disposable.unsubscribeOnClear() {
        compositeDisposable.add(this)
    }

    protected fun <T> Single<T>.withProgress(): Single<T> {
        return doOnSubscribe { showProgress() }
            .doOnSuccess { hideProgress() }
            .doOnError { hideProgress() }
    }

    protected fun fireMessage(@StringRes strRes: Int) {
        fireMessage(appContext.getString(strRes))
    }

    protected fun fireMessage(str: String?) {
        str?.let { _toastMessageLiveData.postValue( SingleEvent(str)) }
    }

    private fun showProgress() {
        _showProgressLiveData.postValue(true)
    }

    private fun hideProgress() {
        _showProgressLiveData.postValue(false)
    }
}
