package motocitizen.presentation.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import com.google.android.material.snackbar.Snackbar
import motocitizen.main.R

abstract class BaseActivity : AppCompatActivity() {

    abstract val layoutResource: Int

    private val appSnackBar by lazy { createErrorSnackBar() }

    override fun onCreate(savedInstanceState: Bundle?) {
        //setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(layoutResource)
    }

    fun openActivity(activity: Class<out Activity>, clearStack: Boolean = false) {
        startActivity(Intent(this, activity).apply {
            if (clearStack) {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        })
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    private fun showSnackBarError(@StringRes textResId: Int) = appSnackBar.setText(textResId).show()

    private fun createErrorSnackBar(): Snackbar {
        val errorSnackBar = Snackbar.make(findViewById(R.id.main_coordinator), "", 3_000)
        val snackBarView = errorSnackBar.view.apply {
            setBackgroundColor(ContextCompat.getColor(context, R.color.brick_red))
        }
        val textView = snackBarView.findViewById(R.id.snackbar_text) as TextView
        TextViewCompat.setTextAppearance(textView, R.style.AppSnackBarText)
        textView.setTextColor(ContextCompat.getColor(this, R.color.white))
        return errorSnackBar
    }
}