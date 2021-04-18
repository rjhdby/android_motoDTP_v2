package motocitizen.ui.rows.accident

import android.content.Context
import com.airbnb.epoxy.ModelView
import motocitizen.utils.makeMargins

@ModelView (autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
abstract class OwnedRow(context: Context) : Row(context) {
    override val margins = makeMargins(2) {
        left = 16
        right = 4
    }
}