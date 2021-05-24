package motocitizen.presentation.screens.accident.details.tabs.messages

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.lcenstate.toLcenEventObservable
import motocitizen.domain.model.message.Message
import motocitizen.domain.usecases.MessageUseCase
import motocitizen.presentation.base.viewmodel.BaseViewModel

class DetailMessagesViewModel @ViewModelInject constructor(
    private val messageUseCase: MessageUseCase
) : BaseViewModel() {

    lateinit var accidentId: String

    private val _loadMessageListState = MutableLiveData<LcenState<List<Message>>>(LcenState.None)
    val loadMessageListState: LiveData<LcenState<List<Message>>>
        get() = _loadMessageListState

    private val _newMessageListState = MutableLiveData<LcenState<Message>>(LcenState.None)
    val newMessageListState: LiveData<LcenState<Message>>
        get() = _newMessageListState

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
                    _loadMessageListState::postValue,
                    ::handleError
                )
        }
    }

    fun createMessage(text: String) {
        safeSubscribe {
            messageUseCase.createMessage(accidentId, text)
                .toLcenEventObservable { it }
                .subscribe(
                    _newMessageListState::postValue,
                    ::handleError
                )
        }
    }
}