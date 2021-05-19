package motocitizen.presentation.screens.auth.vk

import androidx.hilt.lifecycle.ViewModelInject
import motocitizen.data.repos.AuthDataRepo
import motocitizen.presentation.base.viewmodel.BaseViewModel

class AuthVkViewModel @ViewModelInject constructor(
    private val authDataRepo: AuthDataRepo
) : BaseViewModel() {
    fun addToken(token: String?) {
        authDataRepo.saveToken(token)
    }
}