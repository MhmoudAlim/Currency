package com.mahmoudalim.core.utils

/**
 * Created by Mahmoud Alim on 20/03/2022.
 */

interface Mapper<I, O> {

    fun map(input: I): O
}