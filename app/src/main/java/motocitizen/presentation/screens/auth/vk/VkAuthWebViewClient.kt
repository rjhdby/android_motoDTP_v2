package motocitizen.presentation.screens.auth.vk

import android.graphics.Bitmap
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import motocitizen.main.BuildConfig

private const val AUTH_TOKEN_PREFIX = "${BuildConfig.SERVER_URL}/v1/user/register/vk?code="
private const val JS =
    "javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');"

class VkAuthWebViewClient : WebViewClient() {

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        if (url.toString().startsWith(AUTH_TOKEN_PREFIX)) {
            view!!.apply {
                visibility = View.GONE
                loadUrl(JS)
            }
        }
    }
}