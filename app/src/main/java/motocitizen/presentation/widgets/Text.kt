package motocitizen.presentation.widgets

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.core.widget.TextViewCompat
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.google.android.material.textview.MaterialTextView
import motocitizen.presentation.base.getColor

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class Text @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle,
) : MaterialTextView(context, attrs, defStyleAttr) {

    @set:TextProp
    var title: CharSequence?
        get() = text
        set(value) {
            text = value
        }

    @set:CallbackProp
    var clickListener: (() -> Unit)? = null

    init {
        setOnClickListener { clickListener?.invoke() }
    }

    @ModelProp
    override fun setGravity(gravity: Int) {
        super.setGravity(gravity)
    }

    @ModelProp(group = "textColor")
    override fun setTextColor(@ColorInt color: Int) {
        super.setTextColor(color)
    }

    @ModelProp(group = "backgroundColor")
    override fun setBackgroundColor(@ColorInt color: Int) {
        super.setBackgroundColor(color)
    }

    @ModelProp(group = "backgroundColor")
    override fun setBackgroundResource(@DrawableRes resId: Int) {
        super.setBackgroundResource(resId)
    }

    @ModelProp
    fun setTitleAppearance(@StyleRes resId: Int) {
        TextViewCompat.setTextAppearance(this, resId)
    }

    @ModelProp(group = "textColor")
    fun setTextColorRes(@ColorRes resId: Int) {
        if (resId != 0) setTextColor(getColor(resId))
    }

    @ModelProp(group = "backgroundColor")
    fun setBackgroundColorRes(@ColorRes resId: Int) {
        if (resId != 0) setBackgroundColor(getColor(resId))
    }

    @ModelProp
    fun setPadding(viewSpaces: ViewSpaces?) {
    }
}