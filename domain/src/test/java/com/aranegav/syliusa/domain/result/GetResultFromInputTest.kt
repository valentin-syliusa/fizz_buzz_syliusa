package com.aranegav.syliusa.domain.result

import com.aranegav.syliusa.domain.result.model.Input
import com.aranegav.syliusa.domain.result.usecase.GetResultFromInput
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetResultFromInputTest {
    @Test
    fun `Check if result is valid from a given input`() = runBlocking {
        //Given a fake input
        val input = Input(
            int1 = 3,
            int2 = 5,
            limit = 20,
            str1 = "FRG",
            str2 = "Consulting"
        )
        val expectedResult = listOf(
            "1",
            "2",
            "FRG",
            "4",
            "Consulting",
            "FRG",
            "7",
            "8",
            "FRG",
            "Consulting",
            "11",
            "FRG",
            "13",
            "14",
            "FRGConsulting",
            "16",
            "17",
            "FRG",
            "19",
            "Consulting"
        )
        //When we trigger our use case
        val getResultFromInput = GetResultFromInput()
        val result = getResultFromInput.perform(input)
        //Then, we check the validity of our result with the result we were waiting for
        assert(result == expectedResult)
    }
}