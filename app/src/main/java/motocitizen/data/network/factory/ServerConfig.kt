package motocitizen.data.network.factory

import motocitizen.main.BuildConfig
import java.net.URI

class ServerConfig private constructor(
        rootUrl: String,
        apiVersion: String? = null,
        val pin: String? = null
) {
    val apiUrl: String = if (apiVersion == null) {
        URI("$rootUrl/")
    } else {
        URI("$rootUrl/v$apiVersion/")
    }.normalize().toString()

    val host: String = URI(rootUrl).host

    companion object {
        val DEFAULT = Builder(
            BuildConfig.SERVER_URL
        )
            .apiVersion(BuildConfig.API_VERSION)
            .build()
    }

    data class Builder(
        var rootUrl: String,
        var apiVersion: String? = null,
        var pin: String? = null,
        var pin2: String? = null
    ) {

        fun apiVersion(apiVersion: String) = apply { this.apiVersion = apiVersion }
        fun build() = ServerConfig(
                rootUrl,
                apiVersion,
                pin
        )
    }
}