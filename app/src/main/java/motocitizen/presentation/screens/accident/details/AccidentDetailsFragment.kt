package motocitizen.presentation.screens.accident.details

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_accident_details.*
import motocitizen.domain.lcenstate.isContent
import motocitizen.domain.lcenstate.isError
import motocitizen.domain.lcenstate.isLoading
import motocitizen.domain.model.accident.Accident
import motocitizen.domain.model.accident.AccidentHardness
import motocitizen.domain.utils.distanceString
import motocitizen.main.R
import motocitizen.presentation.base.viewmodel.VMFragment

@AndroidEntryPoint
class AccidentDetailsFragment :
    VMFragment<AccidentDetailsViewModel>(R.layout.activity_accident_details) {
    override val viewModel: AccidentDetailsViewModel by viewModels()

    private val args: AccidentDetailsFragmentArgs by navArgs()

    override fun initViewModel() {
        viewModel.loadAccident.observe {
            show_progress.isVisible = it.isLoading()
            error_view.isVisible = it.isError()
            view_panel.isVisible = it.isContent()
            it.asContentOrNull()?.let(::renderContent)
        }
    }

    override fun initUi(savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
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
        val id = item.itemId
        if (id == R.id.action_conflict) {
            viewModel.changeConflict()
        } else if (id == R.id.action_to_map) {
            viewModel.toMap()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val item = menu.findItem(R.id.action_conflict)
        val accident = viewModel.getAccident()
        if (accident != null) {
            if (accident.conflict) {
                item.title = getString(R.string.drop_conflict)
            } else {
                item.title = getString(R.string.set_conflict)
            }
        }
        menu.findItem(R.id.action_to_map).setVisible(args.mapEnable)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAccidentInfo(args.accidentId)
    }

    private fun renderContent(accident: Accident) {
        acc_details_medicine.visibility =
            if (accident.hardness == AccidentHardness.UNKNOWN) View.GONE else View.VISIBLE
        acc_details_medicine.text = getString(R.string.medicine, accident.hardness.text)
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