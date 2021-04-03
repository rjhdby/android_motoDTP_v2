package motocitizen.domain.repos

import io.reactivex.Single

interface WebRepo {
    fun getTinkoffUrl(): Single<String>
}