package motocitizen.domain.usecases

import io.reactivex.Single
import motocitizen.data.network.restrictions.Restrictions
import motocitizen.domain.repos.RestrictionsRepo

class GetRestrictionsUseCase(private val repo: RestrictionsRepo) {

    operator fun invoke(skipCache: Boolean = false): Single<Restrictions> =
        repo.getRestrictions(skipCache)
}