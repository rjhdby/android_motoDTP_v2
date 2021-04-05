package motocitizen.presentation.base

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import kotlin.math.roundToInt

private val Resources.scale: Float
    get() = displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT

private val Context.scale: Float
    get() = resources.scale

fun Context.dpToPx(value: Int): Int = (value * scale).roundToInt()
fun Context.dpToPx(value: Float): Int = (value * scale).roundToInt()
fun Resources.dpToPx(value: Int): Int = (value * scale).roundToInt()
fun Resources.dpToPx(value: Float): Int = (value * scale).roundToInt()

fun Context.dpToPxPrecisely(value: Int): Float = value * scale
fun Context.dpToPxPrecisely(value: Float): Float = value * scale
fun Resources.dpToPxPrecisely(value: Int): Float = value * scale
fun Resources.dpToPxPrecisely(value: Float): Float = value * scale

fun Context.pxToDp(value: Int): Float = value / scale
fun Context.pxToDp(value: Float): Float = value / scale
fun Resources.pxToDp(value: Int): Float = value / scale
fun Resources.pxToDp(value: Float): Float = value / scale