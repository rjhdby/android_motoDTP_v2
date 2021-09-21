package motocitizen.presentation.screens.auth.vk

import dagger.hilt.android.lifecycle.HiltViewModel
import motocitizen.data.repos.AuthDataRepo
import motocitizen.presentation.base.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class AuthVkViewModel @Inject constructor(
    private val authDataRepo: AuthDataRepo
) : BaseViewModel() {
    fun saveToken(token: String?) {
        authDataRepo.saveToken(token)
    }
}