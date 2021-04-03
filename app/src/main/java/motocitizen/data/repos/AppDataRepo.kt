package motocitizen.data.repos

import motocitizen.data.storage.keyvalue.SharedPrefsKey
import motocitizen.data.storage.keyvalue.SharedPrefsStorage
import motocitizen.domain.model.accident.AccidentPush
import motocitizen.domain.model.accident.AccidentPushMap
import motocitizen.domain.repos.AppRepo
import motocitizen.domain.storage.ObjectTransformer
import javax.inject.Inject

class AppDataRepo @Inject constructor(
    private val sharedPrefsStorage: SharedPrefsStorage,
    private val objectTransformer: ObjectTransformer
) : AppRepo {

    override fun saveAccidentPush(push: AccidentPush) {
        val json = sharedPrefsStorage.getString(SharedPrefsKey.AccidentPushData, null)
        val pushes = json?.let {
            objectTransformer.fromString(json, AccidentPushMap::class.java)
        } ?: AccidentPushMap()
        pushes.add(push)
        val updatedJson = objectTransformer.toString(pushes)
        sharedPrefsStorage.putString(SharedPrefsKey.AccidentPushData, updatedJson)
    }
}