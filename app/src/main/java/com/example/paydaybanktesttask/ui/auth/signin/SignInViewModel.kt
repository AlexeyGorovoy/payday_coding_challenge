package com.example.paydaybanktesttask.ui.auth.signin

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.paydaybanktesttask.R
import com.example.paydaybanktesttask.core.SingleEvent
import com.example.paydaybanktesttask.domain.network.model.AuthenticateBody
import com.example.paydaybanktesttask.domain.repository.Repository
import com.example.paydaybanktesttask.ui.BaseViewModel
import com.example.paydaybanktesttask.ui.auth.AuthActivity.Companion.FAKE_DELAY_MSEC
import com.example.paydaybanktesttask.util.*
import retrofit2.HttpException
import java.util.concurrent.TimeUnit

class SignInViewModel(
    private val repository: Repository,
    private val appContext: Context
) : BaseViewModel(appContext) {

    val navigateToInformationSingleLiveData: LiveData<SingleEvent<Long>>
        get() = _navigateToInformationSingleLiveData

    private val _navigateToInformationSingleLiveData = MutableLiveData<SingleEvent<Long>>()

    val navigateToSignUpLiveData: LiveData<SingleEvent<Unit>>
        get() = _navigateToSignUpLiveData

    private val _navigateToSignUpLiveData = MutableLiveData<SingleEvent<Unit>>()

    private val httpValidatorStrings =
        HttpValidatorStrings(
            code404StringRes = R.string.http_code_404_sign_in,
            code500StringRes = R.string.http_code_500
        )

    fun onSubmitButtonClicked(email: String, password: String) {
        if (!isEmailValid(email)) {
            fireMessage(R.string.not_valid_email)
            return
        }

        if (!isPasswordValid(password)) {
            fireMessage(R.string.not_valid_password)
            return
        }

        repository.postAuthenticate(AuthenticateBody(email, password))
            .delay(FAKE_DELAY_MSEC, TimeUnit.MILLISECONDS)
            .fromIoToMain()
            .withProgress()
            .subscribe(
                {
                    fireMessage("Login succeed")
                    _navigateToInformationSingleLiveData.value = SingleEvent(it.id)
                },
                {
                    if (it is HttpException) {
                        catchThrowableWithStringRes(
                            it,
                            appContext,
                            httpValidatorStrings.getForCode(it.code())
                        )
                    } else {
                        catchThrowable(it, appContext)
                    }
                }
            )
            .unsubscribeOnClear()
    }

    fun onRegisterButtonClicked() {
        _navigateToSignUpLiveData.value = SingleEvent(Unit)
    }
}