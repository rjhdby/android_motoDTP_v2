package motocitizen.presentation.screens.accident.create.map

import android.os.Bundle
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_map.*
import motocitizen.main.R
import motocitizen.presentation.base.viewmodel.VMFragment
import motocitizen.presentation.screens.root.RootActivity

@AndroidEntryPoint
class CreateMapFragment : VMFragment<CreateMapViewModel>(R.layout.fragment_create_map) {
    override val viewModel: CreateMapViewModel by viewModels()

    override fun initViewModel() {
    }

    fun toHome() {
        val root = activity as RootActivity
        root.toHome()
    }

    override fun initUi(savedInstanceState: Bundle?) {
        tool_bar.setTitle(getString(R.string.where))
        tool_bar.run {
            onLeftIconClick = { toHome() }
        }
        ADDRESS.setOnClickListener {
            viewModel.navigateToType()
//            builder.location = AccidentLocation(MyLocationManager.getAddress(latLng), latLng)
//            changeFrameTo(TYPE)
//            EmptyAddressDialog(this, builder.location.address, this::addressDialogCallback)
        }
    }
}