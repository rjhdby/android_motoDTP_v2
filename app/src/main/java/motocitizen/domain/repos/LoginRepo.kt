package motocitizen.domain.repos

import io.reactivex.Completable
import motocitizen.domain.model.login.SignIn

interface LoginRepo {
    fun login(data: SignIn): Completable
}