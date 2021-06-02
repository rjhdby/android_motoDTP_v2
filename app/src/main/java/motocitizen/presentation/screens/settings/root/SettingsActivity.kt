package motocitizen.presentation.screens.settings.root

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import motocitizen.main.R
import motocitizen.presentation.base.viewmodel.VMActivity

@AndroidEntryPoint
class SettingsActivity : VMActivity<SettingsActivityViewModel>() {
    override val viewModel: SettingsActivityViewModel by viewModels()
    override val layoutResource: Int = R.layout.activity_settings
    lateinit var navigation: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_settings_fragment) as NavHostFragment
        navigation = navHostFragment.navController
    }
}