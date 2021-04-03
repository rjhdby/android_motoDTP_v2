package motocitizen.presentation.base

import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils.loadAnimation
import android.widget.EditText
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.google.android.material.bottomnavigation.BottomNavigationView
import motocitizen.main.R

fun EditText.onTextChanged(action: (String) -> Unit): TextWatcher {
    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            s ?: return
            action.invoke(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }
    addTextChangedListener(textWatcher)
    return textWatcher
}

fun ViewGroup.inflateViewAsRoot(layoutResId: Int): View? = View.inflate(context, layoutResId, this)

fun View.setSelectableItemBackground() {
    val outValue = TypedValue()
    context.theme.resolveAttribute(R.attr.selectableItemBackground, outValue, true)
    setBackgroundResource(outValue.resourceId)
}

fun AppCompatImageView.setIcon(@DrawableRes iconRes: Int) {
    setImageDrawable(ContextCompat.getDrawable(context, iconRes))
}

private fun BottomNavigationView.show() {
    if (isVisible) return
    val animation = loadAnimation(context, R.anim.slide_up)
    animation.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(p0: Animation?) {
            isVisible = true
        }

        override fun onAnimationEnd(p0: Animation?) = Unit
        override fun onAnimationRepeat(p0: Animation?) = Unit
    })
    startAnimation(animation)
}

private fun BottomNavigationView.hide() {
    if (isGone) return
    val animation = loadAnimation(context, R.anim.slide_down)
    animation.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(p0: Animation?) = Unit

        override fun onAnimationEnd(p0: Animation?) {
            isGone = true
        }

        override fun onAnimationRepeat(p0: Animation?) = Unit
    })
    startAnimation(animation)
}

var BottomNavigationView.isVisibleWithAnimation: Boolean
    get() = visibility == View.VISIBLE
    set(value) = if (value) {
        show()
    } else {
        hide()
    }