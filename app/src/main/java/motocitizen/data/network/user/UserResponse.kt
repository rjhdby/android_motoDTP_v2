package motocitizen.data.network.user

import motocitizen.domain.model.user.UserRole

data class UserResponse(
    val id: String,
    val nick: String,
    val role: UserRole,
)