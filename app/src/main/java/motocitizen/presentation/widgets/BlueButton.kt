package motocitizen.presentation.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.item_button.view.*
import motocitizen.main.R
import motocitizen.presentation.base.inflateViewAsRoot

class BlueButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    init {
        inflateViewAsRoot(R.layout.item_button)
    }

    fun setTitle(title: String) {
        label.text = title
    }
}