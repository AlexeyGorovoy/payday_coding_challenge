package com.example.paydaybanktesttask.ui

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

abstract class BaseFragment : Fragment() {

    private lateinit var baseViewModel: BaseViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseViewModel = provideViewModel()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        baseViewModel.toastMessageLiveData.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        })

        baseViewModel.showProgressLiveData.observe(viewLifecycleOwner, Observer {
            if (it) {
                showProgress()
            } else {
                hideProgress()
            }
        })
    }

    /**
     * Optionally shows progress. You need it you override it
     */
    protected open fun hideProgress() {}

    /**
     * Optionally hides progress. You need it you override it
     */
    protected open fun showProgress() {}

    abstract fun provideViewModel(): BaseViewModel
}