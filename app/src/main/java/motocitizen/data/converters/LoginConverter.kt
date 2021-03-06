package motocitizen.data.converters

import io.reactivex.Completable
import motocitizen.data.network.login.AuthData
import motocitizen.data.network.login.SignInApiRequest
import motocitizen.data.network.login.SignInApiResponse
import motocitizen.data.network.user.User
import motocitizen.data.network.user.UserResponse
import motocitizen.data.utils.getNotNull
import motocitizen.domain.exceptions.ServerException
import motocitizen.domain.model.login.SignIn

object LoginConverter {
    fun toSignInRequest(source: SignIn): SignInApiRequest {
        return source.let {
            SignInApiRequest(
                installId = it.installId,
                token = it.token,
                platform = it.platform
            )
        }
    }

    fun fromSignInError(th: Throwable): Completable {
        if (th !is ServerException) return Completable.error(th)
        val exception = when (th.httpCode) {
            //HttpCodes.FORBIDDEN -> WrongConfirmCodeException()
            else -> th
        }
        return Completable.error(exception)
    }

    fun fromSignInResponse(data: SignInApiResponse?): AuthData {
        val result = getNotNull(data)
        return AuthData(
            //todo Убрать после реализации на сервере.
            //authToken = getNotNull(result.token))
            authToken = "authToken"
        )
    }

    fun toUser(response: UserResponse): User {
        val result = getNotNull(response)
        return result.let {
            val restrictions = User(
                id = it.id,
                nick = it.nick,
                role = it.role,
            )
            restrictions
        }
    }
}