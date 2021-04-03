package motocitizen.data.storage.keyvalue

import motocitizen.domain.storage.keyvalue.Key

sealed class SharedPrefsKey(val name: String) :
    Key {
    object AccidentPushData : SharedPrefsKey("AccidentPushData")
    object AuthData : SharedPrefsKey("AuthData")
    object ClientCertificateAlias : SharedPrefsKey("ClientCertificateAlias")
}