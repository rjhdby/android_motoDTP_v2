package motocitizen.presentation.base.dialog

sealed class DialogButton {
    object Positive : DialogButton()
    object Negative : DialogButton()
}
