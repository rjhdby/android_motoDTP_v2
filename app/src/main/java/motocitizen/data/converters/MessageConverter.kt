package motocitizen.data.converters

import motocitizen.data.network.message.MessageResponse
import motocitizen.data.utils.getNotNull
import motocitizen.domain.model.message.Message
import motocitizen.domain.utils.formatAsISODateTime
import motocitizen.domain.utils.fromUts

object MessageConverter {
    fun toMessageList(response: List<MessageResponse>): List<Message> {
        val result = getNotNull(response)
        return result.map {
            Message(
                id = it.id,
                author = it.author,
                authorNick = it.authorNick,
                topic = it.topic,
                created = it.created.formatAsISODateTime().fromUts(),
                hidden = it.hidden,
                text = it.text,
            )
        }
    }

    fun toMessage(response: MessageResponse): Message {
        val result = getNotNull(response)
        return result.let {
            Message(
                id = it.id,
                author = it.author,
                authorNick = it.authorNick,
                topic = it.topic,
                created = it.created.formatAsISODateTime().fromUts(),
                hidden = it.hidden,
                text = it.text,
            )
        }
    }
}