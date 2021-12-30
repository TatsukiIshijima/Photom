package com.tatsuki.feature.devicecontrol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tatsuki.data.entity.DeviceEntity
import com.tatsuki.data.entity.DeviceType
import com.tatsuki.feature.devicecontrol.databinding.FragmentDeviceControlBinding
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DeviceControlFragment : Fragment() {

    companion object {
        const val SPAN_COUNT = 3
    }

    private var _binding: FragmentDeviceControlBinding? = null
    private val binding get() = _binding!!

    private val deviceControlViewModel: DeviceControlViewModel by viewModels()
    private val groupieAdapter = GroupieAdapter()

    private lateinit var groupLayoutManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeviceControlBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        groupLayoutManager = GridLayoutManager(context, SPAN_COUNT, RecyclerView.VERTICAL, false)
            .apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        val item = groupieAdapter.getItem(position)
                        // Custom Item の getSpanSize で固定値を返すので spanCount はデフォルト値を入力
                        return item.getSpanSize(1, position)
                    }
                }
            }

        binding.deviceControlGridView.apply {
            layoutManager = groupLayoutManager
            adapter = groupieAdapter
        }

        bind()

        deviceControlViewModel.fetchSensorData()
        deviceControlViewModel.fetchDeviceList()
    }

    private fun bind() {
        deviceControlViewModel.loadingFlow
            .onEach { }
            .launchIn(lifecycleScope)

        deviceControlViewModel.localDeviceListFlow
            .filter { it.count() > 0 }
            .onEach {
                val title = resources.getString(R.string.device_item_section_local_device_title)
                val section = Section()
                section.setHeader(DeviceSectionItem(title))
                val items = it.map { entity ->
                    DeviceItem(entity, object : DeviceItem.OnDeviceItemClickedListener {
                        override fun onItemClicked(item: DeviceEntity) {
                            // TODO: add dialog show logic if switch bot device is registered or added.
                        }
                    })
                }
                section.addAll(items)
                groupieAdapter.add(section)
            }
            .launchIn(lifecycleScope)

        deviceControlViewModel.remoteDeviceListFlow
            .filter { it.count() > 0 }
            .onEach {
                val title =
                    resources.getString(R.string.device_item_section_remote_device_section_title)
                val section = Section()
                section.setHeader(DeviceSectionItem(title))
                val items = it.map { entity ->
                    DeviceItem(entity, object : DeviceItem.OnDeviceItemClickedListener {
                        override fun onItemClicked(item: DeviceEntity) {
                            item.type?.let { type ->
                                showControlDialog(type)
                            }
                        }
                    })
                }
                section.addAll(items)
                groupieAdapter.add(section)
            }
            .launchIn(lifecycleScope)

        deviceControlViewModel.sensorDataFlow
            .onEach {
                val section = Section()
                section.setHeader(SensorDataHeaderItem(it))
                groupieAdapter.add(0, section)
            }
            .launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showControlDialog(type: DeviceType) {
        when (type) {
            DeviceType.AirConditioner -> {
                AirConditionerControlDialogFragment()
                    .show(
                        childFragmentManager,
                        AirConditionerControlDialogFragment::class.simpleName
                    )
            }
            DeviceType.Fan, DeviceType.Light -> {
                PowerControlDialogFragment()
                    .show(childFragmentManager, PowerControlDialogFragment::class.simpleName)
            }
            else -> {

            }
        }
    }
}