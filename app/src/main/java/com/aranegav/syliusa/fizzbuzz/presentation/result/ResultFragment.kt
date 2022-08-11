package com.aranegav.syliusa.fizzbuzz.presentation.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aranegav.syliusa.fizzbuzz.databinding.ResultLayoutBinding
import com.aranegav.syliusa.fizzbuzz.presentation.result.list.ResultsListRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResultFragment : Fragment() {
    private var _binding: ResultLayoutBinding? = null

    private val binding: ResultLayoutBinding
        get() = _binding!!

    private val viewModel: ResultViewModel by viewModels()

    private val resultsListRecyclerViewAdapter = ResultsListRecyclerViewAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = ResultLayoutBinding.inflate(inflater, container, false)
        _binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.observableState.collect { state ->
                    //Update results in RecyclerView
                    resultsListRecyclerViewAdapter.results = state.results
                }
            }
        }

        //Set our RecyclerView's settings
        with(binding.resultsRecyclerview) {
            layoutManager = LinearLayoutManager(context)
            adapter = resultsListRecyclerViewAdapter
        }

        //We try to retrieve passed arguments or pop back if arguments couldn't be properly loaded
        arguments?.let { argumentsSafe ->
            val inputData = ResultFragmentArgs.fromBundle(argumentsSafe)
            viewModel.onViewCreated(
                int1 = inputData.int1,
                int2 = inputData.int2,
                limit = inputData.limit,
                str1 = inputData.str1,
                str2 = inputData.str2
            )
        } ?: run {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}