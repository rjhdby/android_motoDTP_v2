package motocitizen.domain.pagingstate

sealed class PagingState<out T : Any> {
    object None : PagingState<Nothing>()
    object Loading : PagingState<Nothing>()
    data class Content<C : Any>(val value: C) : PagingState<C>()
    data class Error(
        val throwable: Throwable,
        val retry: (() -> Unit)?
    ) : PagingState<Nothing>()
}

