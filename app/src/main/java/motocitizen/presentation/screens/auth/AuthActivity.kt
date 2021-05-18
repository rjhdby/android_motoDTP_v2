package motocitizen.presentation.screens.auth

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import motocitizen.main.R
import motocitizen.presentation.base.viewmodel.VMActivity

@AndroidEntryPoint
class AuthActivity : VMActivity<AuthViewModel>() {
    override val viewModel: AuthViewModel by viewModels()
    override val layoutResource: Int = R.layout.activity_auth
    lateinit var navigation: NavController

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_auth_fragment) as NavHostFragment
        navigation = navHostFragment.navController
    }
}