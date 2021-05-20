package motocitizen.domain.repos

import io.reactivex.Single
import motocitizen.domain.model.message.Message

interface MessageRepo {
    fun getMessages(accidentId: String): Single<List<Message>>
}