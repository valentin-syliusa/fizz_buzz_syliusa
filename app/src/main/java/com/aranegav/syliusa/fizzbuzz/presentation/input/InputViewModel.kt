package com.aranegav.syliusa.fizzbuzz.presentation.input

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class State(
    val int1Valid: Boolean,
    val int2Valid: Boolean,
    val limitValid: Boolean,
    val str1Valid: Boolean,
    val str2Valid: Boolean,
    val isValidationButtonEnabled: Boolean
)

sealed class Effect {
    data class NavigateToResultScreen(
        val int1: Int,
        val int2: Int,
        val limit: Int,
        val str1: String,
        val str2: String
    ) : Effect()

    object DisplayInvalidDataError : Effect()
}

class InputViewModel : ViewModel() {
    private val state = MutableLiveData<State>()
    val observableState: LiveData<State> = state

    private val effect = MutableLiveData<Effect>()
    val observableEffect: LiveData<Effect> = effect

    private var int1: Int? = null
    private var int2: Int? = null
    private var limit: Int? = null
    private var str1: String? = null
    private var str2: String? = null

    fun onViewCreated() {
        //Send default state
        state.value = State(
            int1Valid = false,
            int2Valid = false,
            limitValid = false,
            str1Valid = false,
            str2Valid = false,
            isValidationButtonEnabled = false
        )
    }

    fun updateInt1(int1InputAsString: String) {
        int1 = int1InputAsString.toIntOrNull()
        state.value = state.value?.copy(
            int1Valid = int1 != null,
            isValidationButtonEnabled = isValidationButtonEnabled()
        )
    }

    fun updateInt2(int2InputAsString: String) {
        int2 = int2InputAsString.toIntOrNull()
        state.value = state.value?.copy(
            int2Valid = int2 != null,
            isValidationButtonEnabled = isValidationButtonEnabled()
        )
    }

    fun updateLimit(limitInputAsString: String) {
        limit = limitInputAsString.toIntOrNull()
        state.value = state.value?.copy(
            limitValid = limit != null,
            isValidationButtonEnabled = isValidationButtonEnabled()
        )
    }

    fun updateStr1(str1InputAsString: String) {
        str1 = str1InputAsString
        state.value = state.value?.copy(
            str1Valid = !str1.isNullOrEmpty(),
            isValidationButtonEnabled = isValidationButtonEnabled()
        )
    }

    fun updateStr2(str2InputAsString: String) {
        str2 = str2InputAsString
        state.value = state.value?.copy(
            str2Valid = !str2.isNullOrEmpty(),
            isValidationButtonEnabled = isValidationButtonEnabled()
        )
    }

    private fun isValidationButtonEnabled(): Boolean {
        return int1 != null && int2 != null && limit != null && !str1.isNullOrEmpty() && !str2.isNullOrEmpty()
    }

    fun validateInput() {
        val int1 = int1
        val int2 = int2
        val limit = limit
        val str1 = str1
        val str2 = str2
        effect.value =
            if (int1 != null && int2 != null && limit != null && !str1.isNullOrEmpty() && !str2.isNullOrEmpty()) {
                Effect.NavigateToResultScreen(int1, int2, limit, str1, str2)
            } else {
                Effect.DisplayInvalidDataError
            }
    }
}