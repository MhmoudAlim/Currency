package com.mahmoudalim.core.utils

/**
 * Created by Mahmoud Alim on 19/03/2022.
 */
sealed class AppResponse<T>(val data: T?, val message: String?) {
    class Success<T>(data: T) : AppResponse<T>(data, null)
    class ServerError<T>(message: String) : AppResponse<T>(null, message)
    class NetworkError<T>(message: String) : AppResponse<T>(null, message)
}