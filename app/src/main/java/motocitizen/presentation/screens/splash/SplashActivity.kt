package motocitizen.presentation.screens.splash

import android.annotation.SuppressLint
import android.graphics.drawable.Animatable
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.splash.*
import motocitizen.main.R
import motocitizen.presentation.base.viewmodel.VMActivity
import motocitizen.presentation.screens.root.RootActivity

@AndroidEntryPoint
class SplashActivity : VMActivity<SplashViewModel>() {

    override val viewModel: SplashViewModel by viewModels()

    override val layoutResource: Int = R.layout.splash

    private lateinit var frameAnimation: Animatable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    @SuppressLint("RestrictedApi")
    private fun initViews() {
        connection_error.isVisible = false
        action_button.setTitle(getString(R.string.update))
        action_button.setOnClickListener {
            connection_error.isVisible = false
            splash.isVisible = true
            viewModel.work()
        }
        viewModel.errorDesc.observe {
            connection_error.isVisible = true
            splash.isVisible = false
            description_short.text =
                when (it) {
                    SplashViewModel.ErrorType.NO_INTERNET -> getString(R.string.no_internet_short)
                }
            description.text =
                when (it) {
                    SplashViewModel.ErrorType.NO_INTERNET -> getString(R.string.no_internet)
                }

        }
        viewModel.checkState.observe {
            if (it) {
                openActivity(RootActivity::class.java)
            }
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel.onAfterInit()
    }

    override fun onResume() {
        super.onResume()
        if (!this::frameAnimation.isInitialized) {
            frameAnimation = centerDot.background as Animatable
        }
        frameAnimation.start()
        viewModel.work()
    }

    override fun onPause() {
        super.onPause()
        frameAnimation.stop()
    }
}