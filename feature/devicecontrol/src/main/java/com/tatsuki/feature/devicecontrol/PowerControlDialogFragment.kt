package com.tatsuki.feature.devicecontrol

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.tatsuki.feature.devicecontrol.databinding.DialogFragmentPowerControlBinding

class PowerControlDialogFragment : DialogFragment() {

    private var _binding: DialogFragmentPowerControlBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            _binding = DialogFragmentPowerControlBinding.inflate(inflater)
            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null.")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}