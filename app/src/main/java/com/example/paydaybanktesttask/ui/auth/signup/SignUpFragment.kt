package com.example.paydaybanktesttask.ui.auth.signup

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.paydaybanktesttask.R
import com.example.paydaybanktesttask.di.Scopes
import com.example.paydaybanktesttask.ui.BaseFragment
import com.example.paydaybanktesttask.ui.auth.AuthRouter
import com.example.paydaybanktesttask.util.toUiString
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.java.KoinJavaComponent

class SignUpFragment : BaseFragment() {

    private val scope = KoinJavaComponent.getKoin().getScope(Scopes.AUTH_ACTIVITY.name)
    private val viewModel: SignUpViewModel by scope.viewModel(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.registerLiveData.observe(viewLifecycleOwner,
            Observer {
                it.getContentIfNotHandled()?.let {
                    if (activity is AuthRouter) {
                        (activity as AuthRouter).navigateToLogin()
                    }
                }
            })

        viewModel.dateLiveData.observe(viewLifecycleOwner, Observer {
            signUpDateOfBirthEditText.text =
                SpannableStringBuilder().append(it.toUiString())
        })

        signUpDateOfBirthEditText.setOnClickListener { fireDatePicker() }

        signUpSubmitButton.setOnClickListener {
            viewModel.onRegisterButtonClicked(
                RegisterInfo(
                    firstName = signUpFirstNameEditText.text.toString(),
                    lastName = signUpLastNameEditText.text.toString(),
                    phone = signUpPhoneNumberEditText.text.toString(),
                    email = signUpEmailEditText.text.toString(),
                    password = signUpPasswordEditText.text.toString(),
                    confirmPassword = confirmPasswordTEditText.text.toString(),
                    male = signUpMaleSwitch.isChecked
                )
            )
        }
    }

    private fun fireDatePicker() {
        DatePickerDialog(requireContext())
            .apply {
                setOnDateSetListener { _, year, month, day ->
                    viewModel.dateOfBirthPicked(year, month, day)
                }
            }
            .show()
    }

    override fun provideViewModel() = viewModel
}