package motocitizen.ui.rows.accident

import android.content.Context
import com.airbnb.epoxy.ModelView
import motocitizen.utils.makeMargins

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
abstract class OwnedRow(context: Context) : Row(context) {

    companion object {
        private const val DEFAULT_MARGIN = 2
        private const val LEFT_MARGIN = 4
        private const val RIGHT_MARGIN = 16
    }

    override val margins = makeMargins(DEFAULT_MARGIN) {
        left = LEFT_MARGIN
        right = RIGHT_MARGIN
    }
}