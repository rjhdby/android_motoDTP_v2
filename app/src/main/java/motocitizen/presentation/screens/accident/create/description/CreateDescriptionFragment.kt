package motocitizen.presentation.screens.accident.create.description

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_final.*
import motocitizen.app.utils.showKeyboard
import motocitizen.domain.lcenstate.isContent
import motocitizen.domain.lcenstate.isError
import motocitizen.domain.lcenstate.isLoading
import motocitizen.domain.model.accident.AccidentHardness
import motocitizen.main.R
import motocitizen.presentation.base.onTextChanged
import motocitizen.presentation.base.viewmodel.VMFragment
import motocitizen.presentation.screens.root.RootActivity

const val MINIMAL_TEXT_SIZE = 6

@AndroidEntryPoint
class CreateDescriptionFragment :
    VMFragment<CreateDescriptionViewModel>(R.layout.fragment_create_final) {
    override val viewModel: CreateDescriptionViewModel by viewModels()

    private val args: CreateDescriptionFragmentArgs by navArgs()

    override fun initViewModel() {
        viewModel.onAfterInit(
            args.type,
            //todo #69 Переделать когда Custom Enum supports null values
            if (args.hardness != AccidentHardness.NULL) args.hardness else null,
            args.address
        )
    }

    private fun toHome() {
        val navController = findNavController()
        navController.popBackStack(R.id.createMapFragment, false)
        val root = activity as RootActivity
        root.toHome()
    }

    override fun initUi(savedInstanceState: Bundle?) {
        tool_bar.setTitle(getString(R.string.details))
        tool_bar.run {
            onLeftIconClick = viewModel::navigateUp
        }

        create_final_text.onTextChanged { input ->
            requestKeyboard()
            create_button.isEnabled = args.type.isAccident() || input.length > MINIMAL_TEXT_SIZE
        }
        //todo Убрать после отладки
        create_final_text.setText("Ни чего страшного")
        create_button.isEnabled = true
        //todo Убрать после отладки

        create_button.setOnClickListener {
            viewModel.create(create_final_text.text.toString())
        }

        viewModel.newAccident.observe {
            show_progress.isVisible = it.isLoading()
            error_view.isVisible = it.isError()
            result.isVisible = it.isContent()
            panel.isVisible = !it.isContent()
            it.asContentOrNull()?.let { toHome() }
        }
    }

    private fun requestKeyboard() {
        showKeyboard(create_final_text)
    }
}