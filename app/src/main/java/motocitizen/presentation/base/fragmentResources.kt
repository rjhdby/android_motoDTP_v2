package motocitizen.presentation.base

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

@ColorInt
fun Fragment.getColor(@ColorRes colorResId: Int): Int {
    return ContextCompat.getColor(requireContext(), colorResId)
}

fun Fragment.getDrawable(@DrawableRes drawableRes: Int): Drawable? {
    return AppCompatResources.getDrawable(requireContext(), drawableRes)
}

fun Fragment.getDrawableWithTintRes(
    @DrawableRes drawableRes: Int,
    @ColorRes tintColorRes: Int
): Drawable? {
    return if (tintColorRes != 0) {
        getDrawableWithTint(drawableRes, getColor(tintColorRes))
    } else {
        getDrawable(drawableRes)
    }
}

fun Fragment.getDrawableWithTint(
    @DrawableRes drawableRes: Int,
    @ColorInt tintColor: Int
): Drawable? {
    return getDrawable(drawableRes)?.withTint(tintColor)
}

