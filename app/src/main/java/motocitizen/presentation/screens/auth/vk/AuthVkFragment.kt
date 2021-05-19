package motocitizen.presentation.screens.auth.vk

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_vk_auth.*
import motocitizen.main.BuildConfig
import motocitizen.main.R
import motocitizen.presentation.base.viewmodel.VMFragment
import motocitizen.presentation.screens.auth.AuthActivity
import motocitizen.presentation.screens.root.RootActivity

private const val VK_AUTH_URL =
    "https://oauth.vk.com/authorize?client_id=6355438&display=mobile&redirect_uri=${BuildConfig.SERVER_URL}/v1/user/register/vk&response_type=code&v=5.130"
private const val JS_INTERFACE_NAME = "HTMLOUT"

@AndroidEntryPoint
class AuthVkFragment : VMFragment<AuthVkViewModel>(R.layout.fragment_vk_auth) {

    override val viewModel: AuthVkViewModel by viewModels()

    override fun initUi(savedInstanceState: Bundle?) {
        loadWebView()
    }

    private fun loadWebView() {
        configureVkWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun configureVkWebView() {
        val jsInterface = JsInterface(
            viewModel::addToken
        ) { (requireActivity() as AuthActivity).openActivity(RootActivity::class.java, true) }

        auth_vk_webView.apply {
            addJavascriptInterface(
                jsInterface, JS_INTERFACE_NAME
            )
            webViewClient = VkAuthWebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(VK_AUTH_URL)
        }
    }

    override fun initViewModel() {
    }
}