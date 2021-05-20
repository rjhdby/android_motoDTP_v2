package motocitizen.presentation.widgets

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.ModelView
import kotlinx.android.synthetic.main.view_text_skeleton.view.*
import motocitizen.main.R
import motocitizen.presentation.base.inflateViewAsRoot

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class TextSkeletonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        inflateViewAsRoot(R.layout.view_text_skeleton)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        shimmer1.startShimmer()
        shimmer2.startShimmer()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        shimmer1.stopShimmer()
        shimmer2.stopShimmer()
    }
}