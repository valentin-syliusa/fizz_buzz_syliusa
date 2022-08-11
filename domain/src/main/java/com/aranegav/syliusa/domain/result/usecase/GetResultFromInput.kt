package com.aranegav.syliusa.domain.result.usecase

import com.aranegav.syliusa.domain.result.model.Input
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Use case we'll use to retrieve our results from a given Input
 */

class GetResultFromInput @Inject constructor() {
    suspend fun perform(input: Input): List<String> = withContext(Dispatchers.IO) {
        //We generate a list of String that'll be our result
        val result = mutableListOf<String>()
        //For i in a range from 0 until the specified limit, we add a String, following some specific rules
        for (i in 1..input.limit) {
            when {
                //If i is a multiple of int1 & int2, we add a concatenated String with str1 & str2
                i % input.int1 == 0 && i % input.int2 == 0 -> {
                    result.add("${input.str1}${input.str2}")
                }
                //Else, if i is a multiple of int1, we add the specified str1
                i % input.int1 == 0 -> {
                    result.add(input.str1)
                }
                //Else, if i is a multiple of int2 we add the specified str2
                i % input.int2 == 0 -> {
                    result.add(input.str2)
                }
                //If i is not a multiple of int1 or int2, we add i as a String
                else -> {
                    result.add(i.toString())
                }
            }
        }
        result
    }
}