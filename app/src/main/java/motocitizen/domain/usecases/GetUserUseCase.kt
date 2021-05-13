package motocitizen.domain.usecases

import io.reactivex.Single
import motocitizen.data.network.user.User
import motocitizen.domain.repos.UserRepo

class GetUserUseCase(private val repo: UserRepo) {

    operator fun invoke(skipCache: Boolean = false): Single<User> =
        repo.getUser(skipCache)
}