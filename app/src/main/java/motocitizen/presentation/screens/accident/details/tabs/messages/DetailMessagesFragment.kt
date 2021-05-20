package motocitizen.presentation.screens.accident.details.tabs.messages

import android.os.Bundle
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail_messages.*
import motocitizen.main.R
import motocitizen.presentation.base.viewmodel.VMFragment

@AndroidEntryPoint
class DetailMessagesFragment :
    VMFragment<DetailMessagesViewModel>(R.layout.fragment_detail_messages) {

    companion object {
        private const val ACCIDENT_ID = "ACCIDENT_ID"
        fun newInstance(accidentId: String): DetailMessagesFragment {
            return DetailMessagesFragment()
                .apply {
                    arguments = Bundle().apply {
                        putString(ACCIDENT_ID, accidentId)
                    }
                }
        }
    }

    override val viewModel: DetailMessagesViewModel by viewModels()

    private val accidentId by lazy { (arguments as Bundle).getString(ACCIDENT_ID) }

    private val epoxyController by lazy {
        MessagesEpoxyController(
            itemClickListener = viewModel::onItemPressed,
            retryListener = viewModel::loadMessageList
        )
    }

    override fun initViewModel() {
        viewModel.onAfterInit(accidentId!!)
        viewModel.loadMessageListState.observe(epoxyController::setData)
    }

    override fun initUi(savedInstanceState: Bundle?) {
        message_list.setController(epoxyController)
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadMessageList()
    }
}