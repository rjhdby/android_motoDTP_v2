package motocitizen.presentation.screens.accident.details.tabs.messages

import motocitizen.data.network.user.User
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.model.message.Message

data class DetailMessagesViewState(
    val loadMessageListState: LcenState<List<Message>>,
    val newMessageListState: LcenState<Message>,
    val checkUserState: LcenState<User>
)