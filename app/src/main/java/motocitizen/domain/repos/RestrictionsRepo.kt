package motocitizen.domain.repos

import io.reactivex.Single
import motocitizen.data.network.restrictions.Restrictions

interface RestrictionsRepo {
    fun getRestrictions(skipCache: Boolean): Single<Restrictions>
}