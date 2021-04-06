package motocitizen.presentation.screens.map

import android.annotation.SuppressLint
import android.location.Criteria
import android.location.LocationManager
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.lcenstate.toLcenEventObservable
import motocitizen.domain.model.accident.Accident
import motocitizen.data.gps.LocListener
import motocitizen.data.gps.LocationPoint
import motocitizen.domain.usecases.AccidentUseCase
import motocitizen.presentation.base.viewmodel.BaseViewModel




class MapViewModel @ViewModelInject constructor(
    private val getAccidentUseCase: AccidentUseCase
) : BaseViewModel() {






    //todo убрать после реализации входных параметров
    private val token: String = "sasdacxc"
    private val depth: Int = 5
    //todo убрать после реализации входных параметров




    private val _loadAccidentListState = MutableLiveData<LcenState<List<Accident>>>(LcenState.None)
    val loadAccidentListState: LiveData<LcenState<List<Accident>>>
        get() = _loadAccidentListState


    init {
        loadData()

    }


    fun loadData() {
        safeSubscribe {
            getAccidentUseCase.getAccidentList(token = token, depth = depth)
                .toLcenEventObservable { it }
                .subscribe(
                    _loadAccidentListState::postValue,
                    ::handleError
                )
        }
    }




}