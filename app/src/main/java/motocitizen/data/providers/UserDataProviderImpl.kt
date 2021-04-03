package motocitizen.data.providers

import motocitizen.domain.providers.UserDataProvider
import javax.inject.Inject

class UserDataProviderImpl @Inject constructor(
): UserDataProvider {
    override fun getAuthToken(): String? {
        return ""
    }
}