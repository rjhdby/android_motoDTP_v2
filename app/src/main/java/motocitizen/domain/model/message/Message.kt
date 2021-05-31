package motocitizen.domain.model.message

import org.joda.time.DateTime

data class Message(
    val id: String,
    val author: String,
    val authorNick: String,
    val topic: String,
    val created: DateTime,
    val hidden: Boolean,
    val text: String,
)