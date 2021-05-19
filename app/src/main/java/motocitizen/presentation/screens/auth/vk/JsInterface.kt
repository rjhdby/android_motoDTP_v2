package motocitizen.presentation.screens.auth.vk

import android.webkit.JavascriptInterface
import org.jsoup.Jsoup

class JsInterface(val putToken: (String) -> Unit, val openActivity: () -> Unit) {

    @JavascriptInterface
    fun processHTML(html: String?) {
        val doc = Jsoup.parse(html)
        putToken(doc!!.text())
        openActivity.invoke()
    }
}