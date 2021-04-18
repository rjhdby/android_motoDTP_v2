package motocitizen.presentation.screens.home

import com.airbnb.epoxy.EpoxyController
import motocitizen.domain.model.accident.Accident
import motocitizen.ui.rows.accident.*

class AccidentEpoxyController : EpoxyController() {
    lateinit var accidentList: MutableList<Accident>

    fun setAccidents(accident: MutableList<Accident>) {
        accidentList = accident
    }

    override fun buildModels() {
        accidentList.forEachIndexed { index, accident ->
            if (accident.isOwner()) {
                if (accident.hidden) {
                    OwnedHiddenRowModel_().id(index).messages(accident.messagesText())
                        .date(accident.getTimeAfterCreation())
                        .title(accident.title()).addTo(this)
                }
                else {
                    if (accident.resolved == null) {
                        OwnedActiveRowModel_().id(index).messages(accident.messagesText())
                            .date(accident.getTimeAfterCreation())
                            .title(accident.title()).addTo(this)
                        return@forEachIndexed
                    }
                    else {
                        OwnedEndedRowModel_().id(index).messages(accident.messagesText())
                            .date(accident.getTimeAfterCreation())
                            .title(accident.title()).addTo(this)
                        return@forEachIndexed
                    }
                }
            }
            else {
                if (accident.hidden) {
                    HiddenRowModel_().id(index).messages(accident.messagesText())
                        .date(accident.getTimeAfterCreation())
                        .title(accident.title()).addTo(this)
                    return@forEachIndexed
                }
                else {
                    if (accident.resolved == null) {
                        ActiveRowModel_().id(index).messages(accident.messagesText())
                            .date(accident.getTimeAfterCreation())
                            .title(accident.title()).addTo(this)
                        return@forEachIndexed
                    }
                    else {
                        EndedRowModel_().id(index).messages(accident.messagesText())
                            .date(accident.getTimeAfterCreation())
                            .title(accident.title()).addTo(this)
                        return@forEachIndexed
                    }

                }
            }

        }
    }
}