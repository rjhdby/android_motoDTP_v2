package motocitizen.ui.rows.accident

import android.content.Context
import com.airbnb.epoxy.ModelView
import motocitizen.main.R

@ModelView (autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class OwnedHiddenRow(context: Context) : OwnedRow(context) {
    override val textColor: Int = HIDDEN_COLOR
    override val background: Int = R.drawable.owner_accident_hidden
}
