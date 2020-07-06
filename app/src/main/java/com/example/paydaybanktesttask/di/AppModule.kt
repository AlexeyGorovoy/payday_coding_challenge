package com.example.paydaybanktesttask.di

import com.example.paydaybanktesttask.BuildConfig
import com.example.paydaybanktesttask.di.QualifierNames.getNamed
import com.example.paydaybanktesttask.domain.repository.Repository
import com.example.paydaybanktesttask.domain.repository.RepositoryImpl
import com.example.paydaybanktesttask.ui.auth.signin.SignInViewModel
import com.example.paydaybanktesttask.ui.auth.signup.SignUpViewModel
import com.example.paydaybanktesttask.ui.information.dashboard.DashboardViewModel
import com.example.paydaybanktesttask.ui.information.transaction.TransactionsViewModel
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.error.MissingAndroidContextException
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

var baseUrl = BuildConfig.BASE_URL

val appModule = module {

    factory {
        ConnectionPool(20, 5, TimeUnit.MINUTES)
    }

    single {
        OkHttpClient.Builder().run {
            try {
                cache(Cache(androidContext().cacheDir, 10 * 1024 * 1024L)) // 10MB
            } catch (e: MissingAndroidContextException) {
                // do nothing, this is for tests only
            }
            val timeout = if (BuildConfig.DEBUG) 15L else 20L
            readTimeout(timeout, TimeUnit.SECONDS)
            writeTimeout(timeout, TimeUnit.SECONDS)
            connectTimeout(timeout, TimeUnit.SECONDS)
            retryOnConnectionFailure(false)
            connectionPool(get())
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(httpLoggingInterceptor)
            build()
        }
    }

    factory(getNamed { BaseUrl }) {
        baseUrl
    }

    factory<Converter.Factory> {
        GsonConverterFactory.create()
    }

    factory<CallAdapter.Factory> {
        RxJava2CallAdapterFactory.create()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(get<String>(getNamed { BaseUrl }))
            .client(get())
            .addConverterFactory(get())
            .addCallAdapterFactory(get())
            .build()
    }

    single<Repository> {
        RepositoryImpl(get())
    }

    scope(named(Scopes.AUTH_ACTIVITY.name)) {
        viewModel { SignInViewModel(get(), androidContext()) }
        viewModel { SignUpViewModel(get(), androidContext()) }
    }

    scope(named(Scopes.INFORMATION_ACTIVITY.name)) {
        viewModel { TransactionsViewModel(get(), androidContext()) }
        viewModel { DashboardViewModel(get(), androidContext()) }
    }
}

enum class Scopes {
    AUTH_ACTIVITY,
    INFORMATION_ACTIVITY
}