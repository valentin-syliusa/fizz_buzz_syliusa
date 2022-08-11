package com.aranegav.syliusa.fizzbuzz.presentation.input

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class State(
    val int1Valid: Boolean,
    val int2Valid: Boolean,
    val limitValid: Boolean,
    val str1Valid: Boolean,
    val str2Valid: Boolean,
    val isValidationButtonEnabled: Boolean
)

sealed class Effect {
    data class NavigateToResultScreenAndClearInputs(
        val int1: Int,
        val int2: Int,
        val limit: Int,
        val str1: String,
        val str2: String
    ) : Effect()

    object DisplayInvalidDataError : Effect()
}

class InputViewModel : ViewModel() {
    private val state = MutableStateFlow(
        State(
            int1Valid = false,
            int2Valid = false,
            limitValid = false,
            str1Valid = false,
            str2Valid = false,
            isValidationButtonEnabled = false
        )
    )
    val observableState: StateFlow<State> = state

    private val effect = MutableSharedFlow<Effect>(replay = 0)
    val observableEffect: SharedFlow<Effect> = effect

    private var int1: Int? = null
    private var int2: Int? = null
    private var limit: Int? = null
    private var str1: String? = null
    private var str2: String? = null

    fun updateInt1(int1InputAsString: String) {
        int1 = int1InputAsString.toIntOrNull()
        viewModelScope.launch {
            state.emit(
                state.value.copy(
                    int1Valid = int1 != null,
                    isValidationButtonEnabled = isValidationButtonEnabled()
                )
            )
        }
    }

    fun updateInt2(int2InputAsString: String) {
        int2 = int2InputAsString.toIntOrNull()
        viewModelScope.launch {
            state.emit(
                state.value.copy(
                    int2Valid = int2 != null,
                    isValidationButtonEnabled = isValidationButtonEnabled()
                )
            )
        }
    }

    fun updateLimit(limitInputAsString: String) {
        limit = limitInputAsString.toIntOrNull()
        viewModelScope.launch {
            state.emit(
                state.value.copy(
                    limitValid = limit != null,
                    isValidationButtonEnabled = isValidationButtonEnabled()
                )
            )
        }
    }

    fun updateStr1(str1InputAsString: String) {
        str1 = str1InputAsString
        viewModelScope.launch {
            state.emit(
                state.value.copy(
                    str1Valid = !str1.isNullOrEmpty(),
                    isValidationButtonEnabled = isValidationButtonEnabled()
                )
            )
        }
    }

    fun updateStr2(str2InputAsString: String) {
        str2 = str2InputAsString
        viewModelScope.launch {
            state.emit(
                state.value.copy(
                    str2Valid = !str2.isNullOrEmpty(),
                    isValidationButtonEnabled = isValidationButtonEnabled()
                )
            )
        }
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
        viewModelScope.launch {
            effect.emit(
                if (int1 != null && int2 != null && limit != null && !str1.isNullOrEmpty() && !str2.isNullOrEmpty()) {
                    Effect.NavigateToResultScreenAndClearInputs(int1, int2, limit, str1, str2)
                } else {
                    Effect.DisplayInvalidDataError
                }
            )
        }
    }
}