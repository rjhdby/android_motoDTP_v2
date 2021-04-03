package motocitizen.domain.lcenstate

fun <T : Any> LcenState<T>.isContent(): Boolean {
    return this is LcenState.Content
}

