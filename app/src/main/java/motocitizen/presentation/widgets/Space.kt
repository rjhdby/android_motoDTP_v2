package motocitizen.presentation.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Dimension
import androidx.core.view.isInvisible
import androidx.core.view.updateLayoutParams
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import motocitizen.presentation.base.dpToPx

@ModelView(autoLayout = ModelView.Size.WRAP_WIDTH_WRAP_HEIGHT)
class Space @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    init {
        isInvisible = true
    }

    @ModelProp
    fun setHeight(@Dimension sizeDp: Int) {
        updateLayoutParams {
            height = context.dpToPx(sizeDp)
        }
    }

    @ModelProp
    fun setWidth(@Dimension sizeDp: Int) {
        updateLayoutParams {
            width = context.dpToPx(sizeDp)
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun draw(canvas: Canvas) {
        // Ничего не вызываем для оптимизаций
    }
}