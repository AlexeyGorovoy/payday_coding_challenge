package com.example.paydaybanktesttask.domain.network.model

interface Dto<T> {
    fun convert(): T
}
