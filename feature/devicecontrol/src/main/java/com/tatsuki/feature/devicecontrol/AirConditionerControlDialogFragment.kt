package com.tatsuki.feature.devicecontrol

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.tatsuki.feature.devicecontrol.databinding.DialogFragmentAirConditionerControlBinding

class AirConditionerControlDialogFragment : DialogFragment() {

    private var _binding: DialogFragmentAirConditionerControlBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            _binding = DialogFragmentAirConditionerControlBinding.inflate(inflater)
            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null.")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.powerControlButtons.powerOnButton.setOnClickListener {
            dismiss()
        }
        binding.powerControlButtons.powerOffButton.setOnClickListener {
            dismiss()
        }
        binding.airConditionerControlTemperatureUpButton.setOnClickListener {
            updateTemperatureValueText(true)
        }
        binding.airConditionerControlTemperatureDownButton.setOnClickListener {
            updateTemperatureValueText(false)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateTemperatureValueText(isUp: Boolean) {
        val currentValue = binding.airConditionerControlTemperatureValueText.text
            .toString()
            .toInt()
        val newValue = if (isUp) currentValue + 1 else currentValue - 1
        if (newValue in 10..30) {
            binding.airConditionerControlTemperatureValueText.text = newValue.toString()
        }
    }
}