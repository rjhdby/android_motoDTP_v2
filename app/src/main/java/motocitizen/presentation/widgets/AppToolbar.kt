package motocitizen.presentation.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.app_bar.view.*
import motocitizen.main.R
import motocitizen.presentation.base.inflateViewAsRoot

class AppToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var onLeftIconClick: (() -> Unit)? = null

    init {
        inflateViewAsRoot(R.layout.app_bar)
        app_bar_back.setOnClickListener { onLeftIconClick?.invoke() }
    }

    fun setTitle(title: String) {
        app_bar_title.text = title
    }
}