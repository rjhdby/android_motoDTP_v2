package motocitizen.data.network.message

data class MessageResponse(
    val id: String,
    val author: String,
    val authorNick: String,
    val topic: String,
    val created: String,
    val hidden: Boolean,
    val text: String,
)