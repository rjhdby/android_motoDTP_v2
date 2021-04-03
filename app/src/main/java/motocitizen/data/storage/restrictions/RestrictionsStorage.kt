package motocitizen.data.storage.restrictions

import motocitizen.data.network.restrictions.Restrictions
import motocitizen.data.storage.SessionMemory
import motocitizen.data.storage.nullable
import javax.inject.Inject

interface RestrictionsStorage {
    var restrictions: Restrictions?
}

class RestrictionsMemoryStorage @Inject constructor(
    memory: SessionMemory,
) : RestrictionsStorage {
    override var restrictions: Restrictions? by memory.nullable()
}