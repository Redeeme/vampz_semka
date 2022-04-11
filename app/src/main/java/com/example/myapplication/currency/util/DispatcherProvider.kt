package com.example.myapplication.currency.util

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val defaul: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}