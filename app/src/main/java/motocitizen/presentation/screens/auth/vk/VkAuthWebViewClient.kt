package motocitizen.presentation.screens.auth.vk

import android.graphics.Bitmap
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient

class VkAuthWebViewClient : WebViewClient() {
    private val AUTH_TOKEN_PREFIX = "http://188.166.126.84:8080/v1/user/register/vk?code="
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        if (url.toString().startsWith(AUTH_TOKEN_PREFIX)) {
            view!!.apply {
                visibility = View.GONE
                loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');")
            }
        }
    }
}