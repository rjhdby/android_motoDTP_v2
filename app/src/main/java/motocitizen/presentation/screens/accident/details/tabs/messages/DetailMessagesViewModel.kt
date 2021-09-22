package motocitizen.presentation.screens.accident.details.tabs.messages

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.lcenstate.toLcenEventObservable
import motocitizen.domain.model.message.Message
import motocitizen.domain.usecases.GetUserUseCase
import motocitizen.domain.usecases.MessageUseCase
import motocitizen.presentation.base.viewmodel.BaseViewModel
import motocitizen.presentation.base.viewmodel.delegate
import motocitizen.presentation.base.viewmodel.mapDistinct
import javax.inject.Inject

@HiltViewModel
class DetailMessagesViewModel @Inject constructor(
    private val getUser: GetUserUseCase,
    private val messageUseCase: MessageUseCase
) : BaseViewModel() {

    lateinit var accidentId: String

    private val liveState = MutableLiveData(createInitialViewState())
    private var state: DetailMessagesViewState by liveState.delegate()

    val loadMessageListState = liveState.mapDistinct { it.loadMessageListState }
    val newMessageListState = liveState.mapDistinct { it.newMessageListState }
    val checkUserState = liveState.mapDistinct { it.checkUserState }

    private fun createInitialViewState(): DetailMessagesViewState {
        return DetailMessagesViewState(
            loadMessageListState = LcenState.None,
            newMessageListState = LcenState.None,
            checkUserState = LcenState.None,
        )
    }

    fun onAfterInit(accidentId: String) {
        this.accidentId = accidentId
    }

    fun onItemPressed(item: Message) {
        //todo Пригодится модераторам
    }

    fun loadMessageList() {
        safeSubscribe {
            messageUseCase.getMessageList(accidentId)
                .toLcenEventObservable { it }
                .subscribe(
                    { state = state.copy(loadMessageListState = it) },
                    ::handleError
                )
        }
    }

    fun createMessage(text: String) {
        safeSubscribe {
            messageUseCase.createMessage(accidentId, text)
                .toLcenEventObservable { it }
                .subscribe(
                    { state = state.copy(newMessageListState = it) },
                    ::handleError
                )
        }
    }

    fun loadUser() {
        safeSubscribe {
            getUser(skipCache = false)
                .toLcenEventObservable { it }
                .subscribe(
                    { state = state.copy(checkUserState = it) },
                    ::handleError
                )
        }
    }
}