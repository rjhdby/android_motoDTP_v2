package motocitizen.domain.usecases

import motocitizen.domain.repos.MessageRepo
import javax.inject.Inject

class MessageUseCase @Inject constructor(
    private val messageRepo: MessageRepo,
) {
    fun getMessageList(accidentId: String) = messageRepo.getMessages(accidentId)
}