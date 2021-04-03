package motocitizen.domain.exceptions

class NetworkException : Exception()

class BadGatewayException : Exception()

class TimeoutException : Exception()

class FirebaseException : Exception()

class AuthException : Exception()

class LockedException : Exception()

class BadDataException : Exception()

class ServiceUnavailableException : Exception()

class InvalidPushException : Exception()

class ServerException : Exception {
    constructor(httpCode: Int, message: String, details: String) : super(message) {
        this.httpCode = httpCode
        this.details = details
    }

    val httpCode: Int
    val details: String
}
