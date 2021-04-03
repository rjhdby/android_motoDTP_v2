package motocitizen.presentation.screens.root

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import motocitizen.data.network.restrictions.Restrictions
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.lcenstate.toLcenEventObservable
import motocitizen.domain.usecases.GetRestrictionsUseCase
import motocitizen.presentation.base.viewmodel.BaseViewModel
import motocitizen.presentation.base.viewmodel.commands.VMCommand

class RootViewModel @ViewModelInject constructor(
    private val getRestrictions: GetRestrictionsUseCase,
) : BaseViewModel() {

    private val _checkRestrictionsState = MutableLiveData<LcenState<Restrictions>>(LcenState.None)
    val checkRestrictionsState: LiveData<LcenState<Restrictions>>
        get() = _checkRestrictionsState

    fun onAfterInit() {
        checkClientCertificate()
        loadRestrictions()
    }

    private fun loadRestrictions() {
        safeSubscribe {
            getRestrictions(skipCache = true)
                .toLcenEventObservable { it }
                .subscribe(
                    _checkRestrictionsState::postValue,
                    ::handleError
                )
        }
    }

    private fun checkClientCertificate() {
    }

    fun onAliasChosen(alias: String?) {
    }

    fun forceChooseCertificate() {
    }
}

object ChooseCertificate : VMCommand
object ForceChooseCertificate : VMCommand