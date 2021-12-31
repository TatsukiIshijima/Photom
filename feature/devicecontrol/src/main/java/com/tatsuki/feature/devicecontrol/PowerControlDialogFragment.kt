package com.tatsuki.feature.devicecontrol

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.tatsuki.data.entity.DeviceEntity
import com.tatsuki.feature.devicecontrol.databinding.DialogFragmentPowerControlBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PowerControlDialogFragment : DialogFragment() {

    private var _binding: DialogFragmentPowerControlBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var powerControlViewModelAssistedFactory: PowerControlViewModel.Factory

    private val powerControlViewModel: PowerControlViewModel by viewModels {
        val deviceEntity =
            arguments?.getSerializable(POWER_CONTROL_DEVICE_ENTITY_KEY) as DeviceEntity
        PowerControlViewModel.provideFactory(
            powerControlViewModelAssistedFactory,
            deviceEntity
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            _binding = DialogFragmentPowerControlBinding.inflate(inflater)
            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null.")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val entity = arguments?.getSerializable(POWER_CONTROL_DEVICE_ENTITY_KEY) as DeviceEntity

        binding.powerControlHeader.headerTitle.text = entity.name

        binding.powerControlButtons.powerOnButton.setOnClickListener {
            powerControlViewModel.sendPowerCommend(true)
            dismiss()
        }
        binding.powerControlButtons.powerOffButton.setOnClickListener {
            powerControlViewModel.sendPowerCommend(false)
            dismiss()
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val POWER_CONTROL_DEVICE_ENTITY_KEY = "PowerControlDeviceEntityKey"

        fun create(deviceEntity: DeviceEntity): PowerControlDialogFragment {
            val powerControlDialogFragment = PowerControlDialogFragment()
            val args = Bundle()
            args.putSerializable(POWER_CONTROL_DEVICE_ENTITY_KEY, deviceEntity)
            powerControlDialogFragment.arguments = args
            return powerControlDialogFragment
        }
    }
}