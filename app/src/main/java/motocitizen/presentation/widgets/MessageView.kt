package motocitizen.presentation.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import kotlinx.android.synthetic.main.item_message.view.*
import motocitizen.domain.model.message.Message
import motocitizen.domain.utils.asTime
import motocitizen.main.R
import motocitizen.presentation.base.inflateViewAsRoot

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class MessageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    init {
        inflateViewAsRoot(R.layout.item_message)
    }

    @ModelProp
    lateinit var message: Message

    @AfterPropsSet
    fun postBindSetup() {
        message_author.text = message.authorNick
        message_date.text = message.created.asTime()
        message_text.text = message.text
    }

    @set:CallbackProp
    var clickListener: (() -> Unit)? = null

    init {
        setOnClickListener { clickListener?.invoke() }
    }
}