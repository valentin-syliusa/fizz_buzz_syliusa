package com.aranegav.syliusa.fizzbuzz.presentation.result.list

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.aranegav.syliusa.fizzbuzz.databinding.ResultCardLayoutBinding

class ResultCardView(context: Context, attributeSet: AttributeSet? = null) :
    ConstraintLayout(context, attributeSet) {

    private val binding = ResultCardLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    var result: String? = null
        set(value) {
            field = value
            binding.resultTextview.text = value
        }

    init {
        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
    }
}