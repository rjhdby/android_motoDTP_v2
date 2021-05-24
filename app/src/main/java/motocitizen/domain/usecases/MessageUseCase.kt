package motocitizen.domain.usecases

import motocitizen.domain.repos.MessageRepo
import javax.inject.Inject

class MessageUseCase @Inject constructor(
    private val repo: MessageRepo,
) {
    fun getMessageList(accidentId: String) = repo.getMessages(accidentId)
    fun createMessage(accidentId: String, text: String) = repo.createMessage(accidentId, text)
}