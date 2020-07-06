package com.example.paydaybanktesttask.ui.information

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.paydaybanktesttask.R
import com.example.paydaybanktesttask.di.Scopes
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.java.KoinJavaComponent

class InformationActivity : AppCompatActivity() {

    private val scope = KoinJavaComponent.getKoin().getScope(Scopes.INFORMATION_ACTIVITY.name)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        val navView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        val navController = findNavController(R.id.bottomNavigationHostFragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_dashboard, R.id.navigation_transactions)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.selectedItemId = R.id.navigation_dashboard
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) scope.close()
    }

    fun getCustomerId(): Long {
        return intent.getLongExtra(CUSTOMER_ID_EXTRA_KEY, 0)
    }

    companion object {

        const val FAKE_DELAY_MSEC = 1000L

        private const val CUSTOMER_ID_EXTRA_KEY = "CUSTOMER_ID_EXTRA_KEY"

        fun prepareIntent(context: Context, customerId: Long): Intent {
            return Intent(context, InformationActivity::class.java)
                .apply { putExtra(CUSTOMER_ID_EXTRA_KEY, customerId) }
        }
    }
}