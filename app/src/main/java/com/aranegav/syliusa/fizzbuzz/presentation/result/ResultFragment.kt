package com.aranegav.syliusa.fizzbuzz.presentation.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aranegav.syliusa.fizzbuzz.R
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
                launch {
                    viewModel.observableState.collect { state ->
                        //Update results in RecyclerView
                        resultsListRecyclerViewAdapter.results = state.results
                        //Show or hide progress bar following if we have results or not
                        if (state.results.isEmpty()) {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.resultsRecyclerview.visibility = View.GONE
                        } else {
                            binding.progressBar.visibility = View.GONE
                            binding.resultsRecyclerview.visibility = View.VISIBLE
                        }
                    }
                }
                launch {
                    viewModel.observableEffect.collect { effect ->
                        when (effect) {
                            is Effect.NotifyOfTooHighLimitAndGoToPreviousScreen -> {
                                //Notify that limit was too high and go back to input screen
                                AlertDialog.Builder(requireContext())
                                    .setTitle(R.string.too_high_limit)
                                    .setMessage(R.string.too_high_limit_description)
                                    .setPositiveButton(R.string.ok) { _, _ ->
                                        findNavController().popBackStack()
                                    }.show()
                            }
                        }
                    }
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