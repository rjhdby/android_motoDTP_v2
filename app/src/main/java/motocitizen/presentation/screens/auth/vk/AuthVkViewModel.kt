package motocitizen.presentation.screens.auth.vk

import androidx.hilt.lifecycle.ViewModelInject
import motocitizen.data.storage.keyvalue.SharedPrefsKey
import motocitizen.data.storage.keyvalue.SharedPrefsStorageImpl
import motocitizen.presentation.base.viewmodel.BaseViewModel

class AuthVkViewModel @ViewModelInject constructor(
    private val sharedPrefsStorageImpl: SharedPrefsStorageImpl
) : BaseViewModel() {
    fun addToken(token: String?) {
        sharedPrefsStorageImpl.putString(SharedPrefsKey.AuthToken, token)
    }
}