package motocitizen.ui.rows.accident

import android.content.Context
import com.airbnb.epoxy.ModelView
import motocitizen.utils.makeMargins

@ModelView (autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
abstract class CommonRow(context: Context) : Row(context) {
    override val margins = makeMargins(2) {
        left = 4
        right = 16
    }
}
