package com.tatsuki.photom.view.main

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.tatsuki.photom.R
import com.tatsuki.photom.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Skeleton of an Android Things activity.
 *
 * Android Things peripheral APIs are accessible through the PeripheralManager
 * For example, the snippet below will open a GPIO pin and set it to HIGH:
 *
 * val manager = PeripheralManager.getInstance()
 * val gpio = manager.openGpio("BCM6").apply {
 *     setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
 * }
 * gpio.value = true
 *
 * You can find additional examples on GitHub: https://github.com/androidthings
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 左スワイプでメニューが表示するよう設定
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        binding.navView.setupWithNavController(navController)
        binding.navView.setNavigationItemSelectedListener { item ->
            val deepLinkUrl = when (item.itemId) {
                R.id.menu_slide_show_item ->
                    "android-app://com.tatsuki.feature.slideshow/slide_show_fragment"
                R.id.menu_weather_item ->
                    "android-app://com.tatsuki.feature.weather/weather_detail_fragment"
                R.id.menu_device_control_item ->
                    "android-app://com.tatsuki.feature.devicecontrol/device_control_fragment"
                R.id.menu_setting_item ->
                    "android-app://com.tatsuki.feature.setting/setting_fragment"
                else -> ""
            }
            if (deepLinkUrl.isNotEmpty()) {
                val request = NavDeepLinkRequest.Builder
                    .fromUri(deepLinkUrl.toUri())
                    .build()
                navController.navigate(request)
            }
            binding.drawerLayout.closeDrawers()
            true
        }
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.toolbar.title = ""
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onResume() {
        navController.addOnDestinationChangedListener(this)
        super.onResume()
    }

    override fun onPause() {
        navController.removeOnDestinationChangedListener(this)
        super.onPause()
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        binding.toolbar.title = ""
    }
}
