package motocitizen.presentation.screens.accident.details.tabs

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import motocitizen.presentation.screens.accident.details.tabs.messages.DetailMessagesFragment

internal class AccidentDetailAdapter(
    fm: FragmentManager,
    private var typeDetails: List<AccidentDetailTabType>,
    var accidentId: String,
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var m0stFragment: DetailMessagesFragment? = null
//    private var m1ndFragment: DetailVolunteersFragment? = null
//    private var m2ndFragment: DetailHistoryFragment? = null

    override fun getItem(position: Int): Fragment {
        return when (typeDetails[position]) {
            AccidentDetailTabType.Detail -> DetailMessagesFragment.newInstance(accidentId)
//            AccidentTabType.Volunteers -> DetailVolunteersFragment.newInstance(accidentId)
//            AccidentTabType.History -> DetailHistoryFragment.newInstance(accidentId)
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        //return super.instantiateItem(container, position)
        val createdFragment = super.instantiateItem(container, position) as Fragment
        // save the appropriate reference depending on position
        when (typeDetails[position]) {
            AccidentDetailTabType.Detail -> m0stFragment = createdFragment as DetailMessagesFragment
//            AccidentTabType.Volunteers -> m1ndFragment = createdFragment as DetailVolunteersFragment
//            AccidentTabType.History -> m2ndFragment = createdFragment as DetailHistoryFragment
        }
        return createdFragment
    }

    override fun getCount(): Int {
        return typeDetails.size
    }
}