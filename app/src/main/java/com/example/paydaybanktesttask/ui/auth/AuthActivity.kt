package com.example.paydaybanktesttask.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.paydaybanktesttask.R

class AuthActivity : AppCompatActivity(), AuthRouter {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun navigateToRegistration() {
        navController.navigate(R.id.action_signInFragment_to_signUpFragment)
    }

    override fun navigateToLogin() {
        navController.navigate(R.id.action_signUpFragment_to_signInFragment)
    }

    companion object {
        const val FAKE_DELAY_MSEC = 1500L
    }
}