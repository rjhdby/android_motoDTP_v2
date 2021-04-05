package motocitizen.presentation.widgets

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import androidx.core.view.doOnAttach
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import kotlinx.android.synthetic.main.view_error.view.*
import motocitizen.main.R
import motocitizen.presentation.base.dpToPx
import motocitizen.presentation.base.getString
import motocitizen.presentation.base.inflateViewAsRoot
import motocitizen.presentation.base.setSelectableItemBackground

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class ErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        inflateViewAsRoot(R.layout.view_error)
        setSelectableItemBackground()
        context.withStyledAttributes(attrs, R.styleable.ErrorView) {
            text_error_title.text = getString(R.styleable.ErrorView_error_title)
                ?: this@ErrorView.getString(R.string.unable_to_load)
            val heightMode = HeightMode.values()[getInt(R.styleable.ErrorView_heightMode, 0)]
            updateHeight(heightMode)
        }
    }

    @set:TextProp
    var title: CharSequence?
        set(value) {
            text_error_title.text = value ?: getString(R.string.unable_to_load)
        }
        get() = text_error_title.text?.toString()

    @ModelProp
    @JvmOverloads
    fun setHeightMode(heightMode: HeightMode = HeightMode.WRAP_HEIGHT) {
        updateHeight(heightMode)
    }

    @CallbackProp
    @JvmOverloads
    fun setErrorButtonListener(listener: (() -> Unit)? = null) {
        setOnClickListener { listener?.invoke() }
    }

    @ModelProp
    @JvmOverloads
    fun setPadding(spaces: ViewSpaces = ViewSpaces()) {
        updatePadding(
            left = context.dpToPx(spaces.left),
            top = context.dpToPx(spaces.top),
            right = context.dpToPx(spaces.right),
            bottom = context.dpToPx(spaces.bottom)
        )
    }

    private fun updateHeight(heightMode: HeightMode) {
        doOnAttach {
            updateLayoutParams<MarginLayoutParams> {
                height = when (heightMode) {
                    HeightMode.WRAP_HEIGHT -> -2
                    HeightMode.MATCH_HEIGHT -> -1
                }
            }
        }
    }

    enum class HeightMode {
        WRAP_HEIGHT, MATCH_HEIGHT
    }
}