package motocitizen.data.storage.keyvalue

import motocitizen.domain.storage.keyvalue.KeyValueStorage

interface SharedPrefsStorage :
    KeyValueStorage<SharedPrefsKey>