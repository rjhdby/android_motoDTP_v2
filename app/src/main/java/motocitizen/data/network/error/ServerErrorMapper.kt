package motocitizen.data.network.error

import motocitizen.domain.exceptions.*

object ServerErrorMapper {

    fun map(serverException: ServerException): Throwable {
        return when (serverException.httpCode) {
            HttpCodes.NOT_AUTHORIZED -> AuthException()
            HttpCodes.BAD_GATEWAY -> BadGatewayException()
            HttpCodes.SERVICE_UNAVAILABLE -> ServiceUnavailableException()
            HttpCodes.LOCKED -> LockedException()
            else -> serverException
        }
    }
}