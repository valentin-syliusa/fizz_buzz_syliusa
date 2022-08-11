package com.aranegav.syliusa.fizzbuzz.presentation.result.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ResultsListRecyclerViewAdapter : RecyclerView.Adapter<ResultsListRecyclerViewHolder>() {
    var results: List<String> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResultsListRecyclerViewHolder =
        ResultsListRecyclerViewHolder(
            resultCardView = ResultCardView(parent.context)
        )

    override fun onBindViewHolder(holder: ResultsListRecyclerViewHolder, position: Int) {
        holder.bind(results[position])
    }

    override fun getItemCount(): Int = results.size
}