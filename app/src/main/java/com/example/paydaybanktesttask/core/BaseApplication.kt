package com.example.paydaybanktesttask.core

import android.app.Application
import com.example.paydaybanktesttask.di.Scopes
import com.example.paydaybanktesttask.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent


class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(appModule)
        }

        KoinJavaComponent.getKoin()
            .createScope(Scopes.AUTH_ACTIVITY.name, named(Scopes.AUTH_ACTIVITY.name))
    }

    companion object {
        lateinit var instance: BaseApplication
    }
}