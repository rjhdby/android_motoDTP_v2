package motocitizen.data.storage.user

import motocitizen.data.network.user.User
import motocitizen.data.storage.SessionMemory
import motocitizen.data.storage.nullable
import javax.inject.Inject

interface UserStorage {
    var user: User?
}

class UserMemoryStorage @Inject constructor(
    memory: SessionMemory,
) : UserStorage {
    override var user: User? by memory.nullable()
}