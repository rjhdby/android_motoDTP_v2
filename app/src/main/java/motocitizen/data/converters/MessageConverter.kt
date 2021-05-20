package motocitizen.data.converters

import motocitizen.data.network.message.MessageResponse
import motocitizen.data.utils.getNotNull
import motocitizen.domain.model.message.Message

object MessageConverter {
    fun toMessageList(response: List<MessageResponse>): List<Message> {
        val result = getNotNull(response)
        return result.map {
            Message(
                id = it.id,
                author = it.author,
                topic = it.topic,
                created = it.created,
                updated = it.updated,
                hidden = it.hidden,
                text = it.text,
            )
        }
    }
}