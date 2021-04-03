package motocitizen.presentation.base

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import motocitizen.app.App
import motocitizen.app.utils.hideKeyboard
import motocitizen.domain.exceptions.ConvertException
import java.util.*

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    private val singleTaskTimer = Timer()
    private var singleDelayedTask: TimerTask? = null

    // [START declare_analytics]
    lateinit var firebaseAnalytics: FirebaseAnalytics

    // [END declare_analytics]
    val baseActivity: BaseActivity?
        get() = activity as BaseActivity?

    inline fun <reified T> getBaseActivity(): T {
        return activity as? T ?: throw ConvertException()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi(savedInstanceState)
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard(activity)
    }

    override fun onDestroy() {
        super.onDestroy()
        singleTaskTimer.cancel()
    }

    abstract fun initUi(savedInstanceState: Bundle?)

    protected fun delay(duration: Long = App.SEARCH_DELAY_MILLISECONDS, action: () -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            delay(duration)
            action.invoke()
        }
    }

    /****
     * добавить одно действие в очередь, предыдущее действие будет отменено если еще не успело выполниться
     */
    protected fun delaySingle(duration: Long = App.SEARCH_DELAY_MILLISECONDS, action: () -> Unit) {

        singleDelayedTask?.cancel()
        singleTaskTimer.purge()

        val task = object : TimerTask() {
            override fun run() {
                baseActivity?.runOnUiThread {
                    if (!isResumed) return@runOnUiThread

                    action.invoke()
                }
            }
        }

        singleDelayedTask = task
        singleTaskTimer.schedule(task, duration)
    }

    protected fun resizeLayoutForKeyboard() {
        activity?.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
                    or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )
    }
}