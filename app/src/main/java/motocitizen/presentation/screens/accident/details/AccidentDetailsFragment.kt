package motocitizen.presentation.screens.accident.details

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_accident_details.*
import motocitizen.domain.lcenstate.isContent
import motocitizen.domain.lcenstate.isError
import motocitizen.domain.lcenstate.isLoading
import motocitizen.domain.model.accident.Accident
import motocitizen.domain.model.user.UserRole
import motocitizen.domain.utils.distanceString
import motocitizen.main.R
import motocitizen.presentation.base.viewmodel.VMFragment
import motocitizen.presentation.screens.accident.details.tabs.AccidentDetailAdapter
import motocitizen.presentation.screens.accident.details.tabs.AccidentDetailTabType

@AndroidEntryPoint
class AccidentDetailsFragment :
    VMFragment<AccidentDetailsViewModel>(R.layout.fragment_accident_details) {
    override val viewModel: AccidentDetailsViewModel by viewModels()

    private val args: AccidentDetailsFragmentArgs by navArgs()

    override fun initViewModel() {
        viewModel.onAfterInit(args.accidentId, args.user.id)
        viewModel.loadAccident.observe {
            show_progress.isVisible = it.isLoading()
            error_view.isVisible = it.isError()
            view_panel.isVisible = it.isContent()
            it.asContentOrNull()?.let(::renderContent)
        }
        val types = mutableListOf<AccidentDetailTabType>()
        accident_tab.addTab(accident_tab.newTab().setText(R.string.tab_messages))
        types.add(AccidentDetailTabType.Detail)

        accident_view_pager.adapter = AccidentDetailAdapter(
            requireActivity().supportFragmentManager,
            types,
            args.accidentId
        )
    }

    override fun initUi(savedInstanceState: Bundle?) {
        accident_view_pager.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(
                accident_tab
            )
        )
        accident_tab.tabGravity = TabLayout.GRAVITY_FILL
        accident_tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                accident_view_pager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_conflict -> {
                viewModel.changeConflict()
            }
            R.id.action_resolve -> {
                viewModel.resolveReopen()
            }
            R.id.action_to_map -> {
                viewModel.toMap()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        viewModel.getAccident()?.let { accident ->
            val conflictItem = menu.findItem(R.id.action_conflict)
            if (args.user.role >= UserRole.ADMIN) {
                if (accident.conflict) {
                    conflictItem.title = getString(R.string.drop_conflict)
                } else {
                    conflictItem.title = getString(R.string.set_conflict)
                }
                conflictItem.isVisible = true
            } else {
                conflictItem.isVisible = false
            }
            val resolveItem = menu.findItem(R.id.action_resolve)
            if (args.user.role >= UserRole.MODERATOR) {
                if (accident.hidden) {
                    resolveItem.title = getString(R.string.reopen)
                } else {
                    resolveItem.title = getString(R.string.close)
                }
                resolveItem.isVisible = true
            } else {
                resolveItem.isVisible = false
            }
        }
        menu.findItem(R.id.action_to_map).isVisible = args.mapEnable
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAccidentInfo()
    }

    private fun renderContent(accident: Accident) {
        if (accident.hardness != null) {
            acc_details_medicine.text = getString(R.string.medicine, accident.hardness.text)
        } else {
            acc_details_medicine.isVisible = false
        }
        acc_details_general_address.text = accident.location.address
        acc_details_general_description.text = accident.description
        acc_details_general_type.text = accident.type.text
        acc_details_general_time.text = accident.getTimeCreation()
        acc_details_general_distance.text = accident.distanceString()
//todo Разобраться, что-то изменилось
//      acc_details_general_status.visibility = if (accident.status == AccidentStatus.ACTIVE) View.GONE else View.VISIBLE
//      acc_details_general_status.text = accident.status.text
//      acc_details_general_owner.text = accident.owner.name()
    }
}