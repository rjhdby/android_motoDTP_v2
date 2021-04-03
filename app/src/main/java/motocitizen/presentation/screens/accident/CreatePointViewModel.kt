package motocitizen.presentation.screens.accident

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.lcenstate.isContent
import motocitizen.domain.lcenstate.toLcenEventObservable
import motocitizen.presentation.base.viewmodel.BaseViewModel
import motocitizen.presentation.base.viewmodel.delegate
import motocitizen.presentation.base.viewmodel.mapDistinct
//import motocitizen.presentation.screens.home.FUTURE_CHANGES_PAGE
import motocitizen.presentation.screens.home.HomeViewState
//import motocitizen.presentation.screens.home.NOW_CHANGES_PAGE
import org.joda.time.DateTime

class CreatePointViewModel @ViewModelInject constructor(
) : BaseViewModel() {

}
