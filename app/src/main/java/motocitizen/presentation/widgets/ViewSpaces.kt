package motocitizen.presentation.widgets

import androidx.annotation.Dimension

data class ViewSpaces(
    @Dimension val left: Int = 0,
    @Dimension val top: Int = 0,
    @Dimension val right: Int = 0,
    @Dimension val bottom: Int = 0
) {
    constructor(@Dimension horizontal: Int = 0, @Dimension vertical: Int = 0) : this(
        left = horizontal,
        top = vertical,
        right = horizontal,
        bottom = vertical
    )
}