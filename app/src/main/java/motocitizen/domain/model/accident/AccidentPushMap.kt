package motocitizen.domain.model.accident

data class AccidentPushMap(
    private val pushes: HashMap<String, AccidentPush> = hashMapOf()
) {
    operator fun get(operationId: String): AccidentPush? = pushes[operationId]
    fun add(push: AccidentPush) = pushes.put(push.accidentId, push)
}