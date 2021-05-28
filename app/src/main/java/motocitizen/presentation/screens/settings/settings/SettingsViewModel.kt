package motocitizen.presentation.screens.settings.settings

import androidx.hilt.lifecycle.ViewModelInject
import motocitizen.data.network.user.User
import motocitizen.data.repos.SettingsDataRepo
import motocitizen.data.storage.user.UserMemoryStorage
import motocitizen.presentation.base.viewmodel.BaseViewModel

class SettingsViewModel @ViewModelInject constructor(
    private val userMemoryStorage: UserMemoryStorage,
    private val settingsDataRepo: SettingsDataRepo
) : BaseViewModel() {
    private lateinit var tempDeep: String
    private lateinit var tempDistance: String

    fun getUser(): User? {
        return userMemoryStorage.user
    }

    fun updateTempDistance() {
        tempDistance = settingsDataRepo.getDistance()
    }

    fun updateTempDeep() {
        tempDeep = settingsDataRepo.getDeep()
    }

    fun setTempDeep() {
        settingsDataRepo.setDeep(tempDeep)
    }

    fun setTempDistance() {
        settingsDataRepo.setDistance(tempDistance)
    }

    fun getDistance(): String {
        return settingsDataRepo.getDistance()
    }

    fun getDeep(): String {
        return settingsDataRepo.getDeep()
    }
}