package motocitizen.presentation.screens.accident.details.tabs.messages

import com.airbnb.epoxy.TypedEpoxyController
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.model.message.Message
import motocitizen.main.R
import motocitizen.presentation.widgets.*

private const val LOADING_BOTTOM_MARGIN_DP = 12

internal class MessagesEpoxyController(
    private val itemClickListener: (Message) -> Unit,
    private val retryListener: () -> Unit,
) : TypedEpoxyController<LcenState<List<Message>>>() {

    var selectedMessages: List<Message> = emptyList()
        set(value) {
            field = value
            setData(currentData)
        }

    override fun buildModels(data: LcenState<List<Message>>) {
        when (data) {
            LcenState.None -> Unit
            is LcenState.Content -> if (data.value.isEmpty())
                buildEmptyResult()
            else
                buildContent(data.value)
            is LcenState.Error -> buildError()
            LcenState.Loading -> buildLoading()
        }
    }

    private fun buildContent(items: List<Message>) {
        items.forEachIndexed { index, message ->
            messageView {
                id("phase$index")
                message(message)
                clickListener { itemClickListener.invoke(message) }
            }
        }
    }

    private fun buildLoading() {
        for (i in 0 until 4) {
            textSkeletonView {
                id("loading$i")
            }
            space {
                id("loading_space$i")
                height(LOADING_BOTTOM_MARGIN_DP)
            }
        }
    }

    private fun buildError() {
        errorView {
            id("error")
            heightMode(ErrorView.HeightMode.MATCH_HEIGHT)
            errorButtonListener { retryListener.invoke() }
        }
    }

    private fun buildEmptyResult() {
        errorView {
            id("empty_result")
            heightMode(ErrorView.HeightMode.MATCH_HEIGHT)
            errorButtonListener { retryListener.invoke() }
            title(R.string.no_data)
        }
    }
}