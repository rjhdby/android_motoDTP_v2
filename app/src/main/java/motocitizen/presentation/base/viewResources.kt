package motocitizen.presentation.base

import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat

fun View.getString(@StringRes resId: Int): String = context.getString(resId)

fun View.getString(@StringRes resId: Int, vararg formatArgs: Any): String {
    return context.getString(resId, *formatArgs)
}

fun View.getDrawable(@DrawableRes drawableRes: Int): Drawable? {
    return AppCompatResources.getDrawable(context, drawableRes)
}

@ColorInt
fun View.getColor(@ColorRes colorResId: Int): Int = ContextCompat.getColor(context, colorResId)

fun View.getDrawableWithTintRes(
    @DrawableRes drawableRes: Int,
    @ColorRes tintColorRes: Int
): Drawable? {
    return if (tintColorRes != 0) {
        getDrawableWithTint(drawableRes, getColor(tintColorRes))
    } else {
        getDrawable(drawableRes)
    }
}

fun View.getDrawableWithTint(@DrawableRes drawableRes: Int, @ColorInt tintColor: Int): Drawable? {
    return getDrawable(drawableRes)?.withTint(tintColor)
}