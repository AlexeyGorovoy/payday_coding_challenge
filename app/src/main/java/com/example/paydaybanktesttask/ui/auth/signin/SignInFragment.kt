package com.example.paydaybanktesttask.ui.auth.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.paydaybanktesttask.R
import com.example.paydaybanktesttask.di.Scopes
import com.example.paydaybanktesttask.ui.BaseFragment
import com.example.paydaybanktesttask.ui.auth.AuthActivity
import com.example.paydaybanktesttask.ui.auth.AuthRouter
import com.example.paydaybanktesttask.ui.information.InformationActivity
import com.example.paydaybanktesttask.ui.progress.LogoProgressDialog
import kotlinx.android.synthetic.main.fragment_sign_in.*
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent

class SignInFragment : BaseFragment() {

    private val scope = KoinJavaComponent.getKoin().getScope(Scopes.AUTH_ACTIVITY.name)

    private val viewModel: SignInViewModel by scope.viewModel(this)

    private var progressDialog: LogoProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.navigateToInformationSingleLiveData.observe(viewLifecycleOwner,
            Observer {
                it.getContentIfNotHandled()?.let { customerId -> navigateToInformation(customerId) }
            })

        viewModel.navigateToSignUpLiveData.observe(viewLifecycleOwner,
            Observer { it.getContentIfNotHandled()?.let { navigateToRegistration() } })

        signInSubmitButton.setOnClickListener {
            viewModel.onSubmitButtonClicked(
                email = signInEmailEditText.text.toString(),
                password = signInPasswordEditText.text.toString()
            )
        }
        signInRegisterButton.setOnClickListener { viewModel.onRegisterButtonClicked() }
    }

    override fun provideViewModel() = viewModel

    override fun showProgress() {
        activity?.supportFragmentManager?.let {
            LogoProgressDialog().show(it, "")
        }
    }

    override fun hideProgress() {
        progressDialog?.dismiss()
        progressDialog = null
    }

    private fun navigateToInformation(customerId: Long) {
        KoinJavaComponent.getKoin()
            .createScope(Scopes.INFORMATION_ACTIVITY.name, named(Scopes.INFORMATION_ACTIVITY.name))
        activity?.let {
            startActivity(
                InformationActivity.prepareIntent(
                    it.baseContext,
                    customerId
                )
            )

            (activity as AuthActivity).finish()
        }
    }

    private fun navigateToRegistration() {
        if (activity is AuthRouter) {
            (activity as AuthRouter).navigateToRegistration()
        }
    }
}