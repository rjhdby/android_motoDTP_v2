package motocitizen.data.network.factory

import android.content.Context
import android.security.KeyChain
import android.security.KeyChainException
import motocitizen.data.storage.keyvalue.SharedPrefsKey
import motocitizen.data.storage.keyvalue.SharedPrefsStorage
import timber.log.Timber
import java.net.Socket
import java.security.Principal
import java.security.PrivateKey
import java.security.cert.X509Certificate
import javax.net.ssl.X509KeyManager

class CustomKeyManager(
    private val applicationContext: Context,
    private val sharedPreferences: SharedPrefsStorage,
) : X509KeyManager {

    private val clientAliases: Array<String>
        get() {
            val aliases: MutableSet<String> = HashSet()
            val alias = sharedPreferences.getString(SharedPrefsKey.ClientCertificateAlias, null)
            if (alias != null && alias.isNotEmpty()) {
                aliases.add(alias)
            }
            return aliases.toTypedArray()
        }

    override fun getClientAliases(keyType: String?, issuers: Array<out Principal>?): Array<String> {
        Timber.i(clientAliases.toString())
        return clientAliases
    }

    override fun chooseClientAlias(
        keyType: Array<out String>?,
        issuers: Array<out Principal>?,
        socket: Socket?,
    ): String? {
        val alias = sharedPreferences.getString(SharedPrefsKey.ClientCertificateAlias, null)
        Timber.i(alias.toString())
        return alias
    }

    override fun getServerAliases(
        keyType: String?,
        issuers: Array<out Principal>?,
    ): Array<String>? {
        return null
    }

    override fun chooseServerAlias(
        keyType: String?,
        issuers: Array<out Principal>?,
        socket: Socket?,
    ): String? {
        return null
    }

    override fun getCertificateChain(alias: String?): Array<X509Certificate>? {
        val certificates = if (ArrayList(listOf(*clientAliases)).contains(alias)) {
            getCertificatesForAlias(alias)
        } else null
        Timber.i(certificates.toString())
        return certificates
    }

    override fun getPrivateKey(alias: String?): PrivateKey? {
        val privateKeyForAlias = if (ArrayList(listOf(*clientAliases)).contains(alias)) {
            getPrivateKeyForAlias(alias)
        } else null
        Timber.i(privateKeyForAlias.toString())
        return privateKeyForAlias
    }

    private fun getCertificatesForAlias(alias: String?): Array<X509Certificate>? {
        if (alias != null) {
            val getCertificatesForAliasRunnable = GetCertificatesForAliasRunnable(alias)
            val getCertificatesThread = Thread(getCertificatesForAliasRunnable)
            getCertificatesThread.start()
            try {
                getCertificatesThread.join()
                return getCertificatesForAliasRunnable.certificates
            } catch (e: InterruptedException) {
                Timber.e(e)
            }
        }
        return null
    }

    private fun getPrivateKeyForAlias(alias: String?): PrivateKey? {
        if (alias != null) {
            val getPrivateKeyForAliasRunnable = GetPrivateKeyForAliasRunnable(alias)
            val getPrivateKeyThread = Thread(getPrivateKeyForAliasRunnable)
            getPrivateKeyThread.start()
            try {
                getPrivateKeyThread.join()
                return getPrivateKeyForAliasRunnable.privateKey
            } catch (e: InterruptedException) {
                Timber.e(e)
            }
        }
        return null
    }

    private inner class GetCertificatesForAliasRunnable(private val alias: String) : Runnable {
        @Volatile
        lateinit var certificates: Array<X509Certificate>

        override fun run() {
            try {
                certificates =
                    KeyChain.getCertificateChain(applicationContext,
                        alias) as Array<X509Certificate>
            } catch (e: KeyChainException) {
                Timber.e(e)
            } catch (e: InterruptedException) {
                Timber.e(e)
            }
        }
    }

    private inner class GetPrivateKeyForAliasRunnable(private val alias: String) : Runnable {
        @Volatile
        var privateKey: PrivateKey? = null
            private set

        override fun run() {
            try {
                privateKey = KeyChain.getPrivateKey(applicationContext, alias)
            } catch (e: KeyChainException) {
                Timber.e(e)
            } catch (e: InterruptedException) {
                Timber.e(e)
            }
        }
    }
}