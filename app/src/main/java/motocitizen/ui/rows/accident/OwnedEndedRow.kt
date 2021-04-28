package motocitizen.ui.rows.accident

import android.content.Context
import com.airbnb.epoxy.ModelView
import motocitizen.main.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class OwnedEndedRow(context: Context) : OwnedRow(context) {
    override val textColor = ENDED_COLOR
    override val background = R.drawable.owner_accident_ended
}