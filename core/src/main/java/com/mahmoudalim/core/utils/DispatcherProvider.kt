package com.mahmoudalim.core.utils

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Mahmoud Alim on 19/03/2022.
 */
interface DispatcherProvider {

    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}