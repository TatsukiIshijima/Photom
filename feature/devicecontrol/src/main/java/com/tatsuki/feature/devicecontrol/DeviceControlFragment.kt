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
import com.tatsuki.feature.devicecontrol.databinding.FragmentDeviceControlBinding
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DeviceControlFragment : Fragment() {

    private var _binding: FragmentDeviceControlBinding? = null
    private val binding get() = _binding!!

    private val deviceControlViewModel: DeviceControlViewModel by viewModels()
    private val groupieAdapter = GroupieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeviceControlBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.deviceControlGridView.apply {
            layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
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
                            // TODO: show custom dialog
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
                            // TODO: show custom dialog
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
}