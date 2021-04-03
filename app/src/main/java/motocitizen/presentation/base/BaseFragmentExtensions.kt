package motocitizen.presentation.base

import androidx.annotation.StringRes
import motocitizen.main.R
import motocitizen.presentation.base.dialog.BaseDialog

fun BaseFragment.showSimpleDialogWithButton(
    @StringRes textResId: Int,
    message: String? = null,
    cancellable: Boolean = true
) = showSimpleDialog(textResId, message, R.string.ok, cancellable)

fun BaseFragment.showSimpleDialog(
    @StringRes textResId: Int,
    message: String? = null,
    @StringRes positive: Int? = null,
    cancellable: Boolean = true
) = BaseDialog.newInstance(
    text = message,
    title = textResId,
    positive = positive,
).apply {
    setCanceledOnTouchOutside(cancellable)
}
    .show(this)