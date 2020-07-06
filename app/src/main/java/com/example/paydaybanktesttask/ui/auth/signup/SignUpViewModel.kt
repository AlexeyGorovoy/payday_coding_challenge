package com.example.paydaybanktesttask.ui.auth.signup

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.paydaybanktesttask.core.SingleEvent
import com.example.paydaybanktesttask.domain.repository.Repository
import com.example.paydaybanktesttask.ui.BaseViewModel
import java.util.*

class SignUpViewModel(private val repository: Repository, appContext: Context) :
    BaseViewModel(appContext) {

    val registerLiveData: LiveData<SingleEvent<Unit>>
        get() = _registerLiveData

    private val _registerLiveData = MutableLiveData<SingleEvent<Unit>>()

    // Here the date for the further registration lives
    val dateLiveData: LiveData<Date>
        get() = _dateLiveData

    private val _dateLiveData = MutableLiveData<Date>()

    fun onRegisterButtonClicked(registerInfo: RegisterInfo) {
        registerInfo.getValidationStringResMessageIfNeeded()?.let {
            fireMessage(it)
            return
        }

        fireMessage("Registration is not implemented in the API")
    }

    fun dateOfBirthPicked(year: Int, month: Int, day: Int) {
        _dateLiveData.value = GregorianCalendar.getInstance()
            .apply { set(Calendar.YEAR, year) }
            .apply { set(Calendar.MONTH, month) }
            .apply { set(Calendar.DAY_OF_MONTH, day) }
            .time
    }
}