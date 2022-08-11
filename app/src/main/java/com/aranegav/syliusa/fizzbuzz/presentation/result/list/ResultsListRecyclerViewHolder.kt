package com.aranegav.syliusa.fizzbuzz.presentation.result.list

import androidx.recyclerview.widget.RecyclerView

class ResultsListRecyclerViewHolder(private val resultCardView: ResultCardView) :
    RecyclerView.ViewHolder(resultCardView) {
    fun bind(result: String) {
        resultCardView.result = result
    }
}