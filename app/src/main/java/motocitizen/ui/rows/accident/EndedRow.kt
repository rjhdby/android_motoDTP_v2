package motocitizen.ui.rows.accident

import android.content.Context
import com.airbnb.epoxy.ModelView
import motocitizen.main.R

@ModelView (autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class EndedRow constructor(context: Context) : CommonRow(context) {
    override val textColor: Int = ENDED_COLOR
    override val background: Int = R.drawable.accident_row_ended
}
