package motocitizen.ui.rows.accident

//import android.view.View

import android.content.Context
import android.graphics.Typeface
import android.view.Gravity
import android.widget.FrameLayout
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import motocitizen.main.R
import motocitizen.utils.Margins
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.textView
import org.jetbrains.anko.wrapContent

//todo refactor

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
abstract class Row protected constructor(context: Context) :
    FrameLayout(context) {
    companion object {
        const val ACTIVE_COLOR = 0x70FFFFFF
        const val ENDED_COLOR = 0x70FFFFFF
        const val HIDDEN_COLOR = 0x30FFFFFF
    }

    abstract val background: Int
    abstract val textColor: Int
    abstract val margins: Margins


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        setBackgroundResource(background)
        setMargins()

//        setUpListeners()
    }


    private var titleText = textView()
    private var dateText = textView()
    private var messageText = textView()

    @TextProp
    fun setTitle(title: CharSequence) {
        titleText.apply {
            text = (context.resources.getString(R.string.accident_row_content, title))
            layoutParams = LayoutParams(matchParent, wrapContent)
            minLines = 3
            setTextColor(textColor)

        }

    }

    @TextProp
    fun setDate(data: CharSequence) {
        dateText.apply {
            text = data.toString()
            layoutParams = LayoutParams(matchParent, matchParent)
            gravity = Gravity.END
            typeface = Typeface.DEFAULT_BOLD
        }
    }

    @TextProp
    fun setMessages(message: CharSequence) {
        messageText.apply {
            text = message
            layoutParams = LayoutParams(matchParent, matchParent)
            gravity = Gravity.BOTTOM or Gravity.END
        }
    }


    private fun setMargins() {
        val layoutPar = layoutParams as MarginLayoutParams
        layoutPar.width = matchParent
        layoutPar.height = wrapContent
        layoutPar.setMargins(margins.left, margins.top, margins.right, margins.bottom)


    }

//    private fun setUpListeners() {
//        setOnClickListener { clickListener() }
//        setOnLongClickListener { longClickListener(it) }
//    }

//    private fun clickListener() {
//        (context as Activity).goTo(Screens.DETAILS, mapOf(AccidentDetailsActivity.ACCIDENT_ID_KEY to accident.id))
//    }
//
//    private fun longClickListener(v: View): Boolean {
//        AccidentContextMenu(context, accident).showAsDropDown(v)
//        return true
//    }
}