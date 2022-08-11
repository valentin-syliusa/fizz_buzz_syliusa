package com.aranegav.syliusa.fizzbuzz.presentation.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aranegav.syliusa.domain.result.model.Input
import com.aranegav.syliusa.domain.result.usecase.GetResultFromInput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class State(
    val results: List<String>
)

sealed class Effect {
    object NotifyOfTooHighLimitAndGoToPreviousScreen: Effect()
}

@HiltViewModel
class ResultViewModel @Inject constructor(private val getResultFromInput: GetResultFromInput) :
    ViewModel() {

    private val state = MutableStateFlow(State(results = listOf()))
    val observableState: StateFlow<State> = state

    private val effect = MutableSharedFlow<Effect>()
    val observableEffect: SharedFlow<Effect> = effect

    fun onViewCreated(int1: Int, int2: Int, limit: Int, str1: String, str2: String) {
        viewModelScope.launch {
            val input = Input(int1, int2, limit, str1, str2)
            try {
                //Retrieve results and emit
                val results = getResultFromInput.perform(input)
                state.emit(
                    State(
                        results = results
                    )
                )
            } catch (throwable: Throwable) {
                if (throwable is OutOfMemoryError) {
                    //If we throw an OutOfMemoryError during results generation send an effect to notify user and go back to input screen
                    effect.emit(Effect.NotifyOfTooHighLimitAndGoToPreviousScreen)
                }
            }
        }
    }
}