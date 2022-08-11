package com.aranegav.syliusa.fizzbuzz.presentation.input

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.aranegav.syliusa.fizzbuzz.R
import com.aranegav.syliusa.fizzbuzz.databinding.InputLayoutBinding

class InputFragment : Fragment() {
    private var _binding: InputLayoutBinding? = null

    private val binding: InputLayoutBinding
        get() = _binding!!

    private val viewModel: InputViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = InputLayoutBinding.inflate(inflater, container, false)
        _binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observableState.observe(viewLifecycleOwner) {
            it?.let { state ->
                //Update each field's validity state
                binding.int1ValueInputView.state =
                    getString(if (state.int1Valid) R.string.valid_input else R.string.invalid_input)
                binding.int2ValueInputView.state =
                    getString(if (state.int2Valid) R.string.valid_input else R.string.invalid_input)
                binding.limitValueInputView.state =
                    getString(if (state.limitValid) R.string.valid_input else R.string.invalid_input)
                binding.str1ValueInputView.state =
                    getString(if (state.str1Valid) R.string.valid_input else R.string.invalid_input)
                binding.str2ValueInputView.state =
                    getString(if (state.str2Valid) R.string.valid_input else R.string.invalid_input)
                //Enable validation button if needed
                binding.validateButton.isEnabled = state.isValidationButtonEnabled
            }
        }

        viewModel.observableEffect.observe(viewLifecycleOwner) {
            it?.let { effect ->
                when (effect) {
                    is Effect.DisplayInvalidDataError -> {
                        //Display an alert if data is invalid
                        AlertDialog.Builder(requireContext()).setTitle(R.string.invalid_input)
                            .setMessage(R.string.invalid_input_description)
                            .setPositiveButton(R.string.ok, null).show()
                    }
                    is Effect.NavigateToResultScreen -> {
                        //TODO -> Implement navigation to next screen
                    }
                }
            }
        }

        //Set listeners for all inputs & button
        binding.int1ValueInputView.onInputChange = { updatedInt1Input ->
            viewModel.updateInt1(updatedInt1Input)
        }
        binding.int2ValueInputView.onInputChange = { updatedInt2Input ->
            viewModel.updateInt2(updatedInt2Input)
        }
        binding.limitValueInputView.onInputChange = { updatedLimitInput ->
            viewModel.updateLimit(updatedLimitInput)
        }
        binding.str1ValueInputView.onInputChange = { updatedStr1Input ->
            viewModel.updateStr1(updatedStr1Input)
        }
        binding.str2ValueInputView.onInputChange = { updatedStr2Input ->
            viewModel.updateStr2(updatedStr2Input)
        }

        binding.validateButton.setOnClickListener {
            viewModel.validateInput()
        }

        viewModel.onViewCreated()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}