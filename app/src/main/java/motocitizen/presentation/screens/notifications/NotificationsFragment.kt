package motocitizen.presentation.screens.notifications

import android.os.Bundle
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import motocitizen.main.R
import motocitizen.presentation.base.viewmodel.VMFragment

@AndroidEntryPoint
class NotificationsFragment : VMFragment<NotificationsViewModel>(R.layout.fragment_notifications) {

    override val viewModel: NotificationsViewModel by viewModels()

    override fun initViewModel() {
    }

    override fun initUi(savedInstanceState: Bundle?) {
    }
}