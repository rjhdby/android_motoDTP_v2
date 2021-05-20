package motocitizen.data.repos

import io.reactivex.Single
import motocitizen.data.converters.MessageConverter
import motocitizen.data.network.message.MessageApi
import motocitizen.domain.model.message.Message
import motocitizen.domain.repos.MessageRepo
import motocitizen.domain.utils.schedulersIoToMain
import javax.inject.Inject

class MessageDataRepo @Inject constructor(
    private val api: MessageApi,
) : MessageRepo {
    override fun getMessages(accidentId: String): Single<List<Message>> {
        return api.getMessages(accidentId)
            .map { MessageConverter.toMessageList(it) }
            .schedulersIoToMain()
    }
}