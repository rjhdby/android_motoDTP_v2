package motocitizen.ui.rows.accident

import android.content.Context
import com.airbnb.epoxy.ModelView
import motocitizen.main.R

@ModelView (autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class ActiveRow(context: Context) : CommonRow(context) {
    override val textColor: Int = ACTIVE_COLOR
    override val background: Int = R.drawable.message_row
}
