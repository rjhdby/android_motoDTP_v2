package motocitizen.presentation.screens.auth.rootAuth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_auth.*
import motocitizen.main.R
import motocitizen.presentation.base.viewmodel.VMFragment
import motocitizen.presentation.screens.auth.AuthActivity

@AndroidEntryPoint
class AuthTypeFragment : VMFragment<AuthTypeViewModel>(R.layout.fragment_auth),
    View.OnClickListener {
    override val viewModel: AuthTypeViewModel by viewModels()

    override fun initViewModel() {
    }

    override fun initUi(savedInstanceState: Bundle?) {
        auth_vk_btn.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val action: NavDirections
        when (p0!!) {
            auth_vk_btn -> {
                action = AuthTypeFragmentDirections.actionAuthFragmentToAuthVkFragment()
                (requireActivity() as AuthActivity).navigation.navigate(
                    action
                )
            }
        }
    }
}