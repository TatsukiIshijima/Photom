package com.tatsuki.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tatsuki.data.entity.AddressEntity
import com.tatsuki.setting.databinding.FragmentSettingBinding
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private val settingViewModel: SettingViewModel by viewModels()
    private val prefectureGroupieAdapter = GroupieAdapter()
    private val cityGroupieAdapter = GroupieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(layoutInflater, container, false)
//            .apply {
//                settingScreen.setContent {
//                    ScreenContent()
//                }
//            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.prefectureList.apply {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = prefectureGroupieAdapter
        }

        binding.cityList.apply {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = cityGroupieAdapter
        }

        bind()

        showPrefectures()
    }

    private fun bind() {
        settingViewModel.loadingFlow
            .onEach { binding.progressbar.isVisible = it }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        settingViewModel.cityNameListFlow
            .onEach { showCities(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun showPrefectures() {
        val prefecturesItem = AddressEntity.Prefecture::class
            .sealedSubclasses
            .mapNotNull {
                it.objectInstance
            }.map {
                AddressItem(it, object : AddressItem.OnAddressItemClickedListener {
                    override fun onItemClicked(item: AddressEntity) {
                        if (item is AddressEntity.Prefecture) {
                            settingViewModel.fetchCityNameList(item)
                        }
                    }
                })
            }
        val section = Section()
        section.addAll(prefecturesItem)
        prefectureGroupieAdapter.add(section)
    }

    private fun showCities(cityList: List<AddressEntity.City>) {
        val citiesItem = cityList.map {
            AddressItem(it, object : AddressItem.OnAddressItemClickedListener {
                override fun onItemClicked(item: AddressEntity) {
                    // TODO("Not yet implemented")
                }
            })
        }
        if (citiesItem.isEmpty()) {
            return
        }
        cityGroupieAdapter.clear()
        val section = Section()
        section.addAll(citiesItem)
        cityGroupieAdapter.add(section)
    }
}


// Compose の Column のスクロールが効かない
// Compose の LazyColumn だと Item が重ねて表示される
// 上記二つの理由から一旦採用見送り
//@Preview
//@Composable
//private fun ScreenContent() {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//    ) {
//        LocationList()
//    }
//}

//@Composable
//private fun LocationList() {
//    Row(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        val prefectures = PrefectureEntity::class.sealedSubclasses.mapNotNull { it.objectInstance }
//        LazyColumn(
//            modifier = Modifier
//                .background(Color.LightGray)
//                .weight(1f),
//            verticalArrangement = Arrangement.spacedBy(4.dp)
//        ) {
//            items(prefectures) { prefecture ->
//                LocationItem(prefecture)
//            }
//        }
//        val cities = mutableListOf<PrefectureEntity>()
//        cities.add(PrefectureEntity.Hokkaido)
//        LazyColumn(
//            modifier = Modifier
//                .weight(1f),
//            verticalArrangement = Arrangement.spacedBy(4.dp)
//        ) {
//            items(cities) { city ->
//                LocationItem(city)
//            }
//        }

//        Column(
//            modifier = Modifier
//                .background(Color.LightGray)
////                .verticalScroll(rememberScrollState())
//                .weight(1f),
//        ) {
//            PrefectureEntity::class.sealedSubclasses.forEach {
//                it.objectInstance?.let { prefecture ->
//                    LocationItem(prefecture)
//                }
//            }
//        }
//        Column(
//            modifier = Modifier
////                .verticalScroll(rememberScrollState())
//                .weight(1f),
//        ) {
//            LocationItem(PrefectureEntity.Hokkaido)
//        }
//    }
//}

//@Composable
//private fun LocationItem(
//    prefectureEntity: PrefectureEntity
//) {
//    Text(
//        text = prefectureEntity.name,
//        modifier = Modifier
//            .padding(horizontal = 8.dp)
//            .wrapContentWidth(Alignment.Start)
//            .wrapContentHeight(Alignment.CenterVertically)
//    )
//}