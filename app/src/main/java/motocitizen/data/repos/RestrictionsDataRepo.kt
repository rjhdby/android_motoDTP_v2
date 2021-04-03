package motocitizen.data.repos

import io.reactivex.Single
import motocitizen.data.converters.LoginConverter
import motocitizen.data.network.restrictions.Restrictions
import motocitizen.data.network.restrictions.RestrictionsApi
import motocitizen.data.storage.restrictions.RestrictionsStorage
import motocitizen.domain.repos.RestrictionsRepo
import motocitizen.domain.utils.schedulersIoToMain

class RestrictionsDataRepo(
    private val api: RestrictionsApi,
    private val restrictionsStorage: RestrictionsStorage,
) : RestrictionsRepo {

    override fun getRestrictions(skipCache: Boolean): Single<Restrictions> {
        return Single.defer {
            val restrictions = restrictionsStorage.restrictions
            if (skipCache || restrictions == null) {
                api.restrictions()
                    .map { LoginConverter.toRestrictions(it) }
                    .doOnSuccess { restrictionsStorage.restrictions = it }
            } else {
                Single.just(restrictions)
            }
        }
            .schedulersIoToMain()
    }
}