package motocitizen.presentation.screens.accident.details.tabs.messages

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail_messages.*
import motocitizen.data.network.user.User
import motocitizen.domain.model.user.UserRole
import motocitizen.main.R
import motocitizen.presentation.base.viewmodel.VMFragment
import timber.log.Timber

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
        viewModel.newMessageListState.observe {
            new_message_text.text.clear()
            viewModel.loadMessageList()
        }
        viewModel.checkUserState.observe { checkRestrictionsState ->
            checkRestrictionsState.asErrorOrNull()?.let { renderError(it) }
            checkRestrictionsState.asContentOrNull()?.let { renderContent(it) }
        }
    }

    override fun initUi(savedInstanceState: Bundle?) {
        message_list.setController(epoxyController)
        new_message_send.setOnClickListener {
            val text = new_message_text.text.toString().trim()
            if (text.isNotEmpty()) {
                viewModel.createMessage(text)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadUser()
        viewModel.loadMessageList()
    }

    private fun renderContent(user: User) {
        new_message_text.isVisible = user.role != UserRole.READ_ONLY
        new_message_send.isVisible = user.role != UserRole.READ_ONLY
    }

    private fun renderError(throwable: Throwable) {
        //todo Выводить сообщение "Ошибка при получении прав - включен режим "только чтение"
        Timber.e(throwable.toString())
        new_message_text.isVisible = false
        new_message_send.isVisible = false
    }
}