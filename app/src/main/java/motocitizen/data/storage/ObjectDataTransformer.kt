package motocitizen.data.storage

import motocitizen.data.network.factory.GsonFactory
import motocitizen.domain.storage.ObjectTransformer
import javax.inject.Inject

class ObjectDataTransformer @Inject constructor(): ObjectTransformer {

    override fun toString(src: Any): String = GsonFactory.appGson.toJson(src)

    override fun <T> fromString(src: String, classOfT: Class<T>): T = GsonFactory.appGson.fromJson(src, classOfT)
}