package motocitizen.utils

import android.widget.LinearLayout

class Margins(var left: Int = 0, var top: Int = 0, var right: Int = 0, var bottom: Int = 0)

fun makeMargins(default: Int = 0, init: Margins.() -> Unit): Margins {
    val margins = Margins(default, default, default, default)
    margins.init()
    return margins
}

fun LinearLayout.LayoutParams.margins(m: Margins): LinearLayout.LayoutParams {
    setMargins(m.left, m.top, m.right, m.bottom)
    return this
}