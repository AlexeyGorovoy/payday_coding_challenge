package com.example.paydaybanktesttask.di

import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named

object QualifierNames {
    const val BaseUrl = "BaseUrl"
    const val BaseAuthInterceptor = "BaseAuthInterceptor"
    const val TokenInterceptor = "TokenInterceptor"

    fun getNamed(create: QualifierNames.() -> String): Qualifier {
        return named(create(QualifierNames))
    }
}