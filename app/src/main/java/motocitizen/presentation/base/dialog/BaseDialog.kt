package motocitizen.presentation.base.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.dialog_base.*
import motocitizen.main.R
import motocitizen.presentation.base.setIcon

open class BaseDialog : DialogFragment() {
    companion object {
        private const val NAME = "NAME"
        private const val TITLE = "TITLE"
        private const val ICON = "ICON"
        private const val TEXT = "TEXT"
        private const val POSITIVE = "POSITIVE"
        private const val NEGATIVE = "NEGATIVE"

        fun newInstance(
            name: String = "321",
            @StringRes title: Int? = null,
            @DrawableRes icon: Int? = null,
            text: String? = null,
            @StringRes positive: Int? = null,
            @StringRes negative: Int? = null
        ): BaseDialog {
            return BaseDialog().apply {
                arguments = Bundle().apply {
                    putString(NAME, name)
                    title?.let { putInt(TITLE, it) }
                    icon?.let { putInt(ICON, it) }
                    text?.let { putString(TEXT, it) }
                    positive?.let { putInt(POSITIVE, it) }
                    negative?.let { putInt(NEGATIVE, it) }
                }
            }
        }
    }

    private val name by lazy { arguments?.getString(NAME) }
    private val title by lazy { arguments?.getInt(TITLE) }
    private val icon by lazy { arguments?.getInt(ICON) }
    private val textid by lazy { arguments?.getInt(TEXT) }
    private val text by lazy { arguments?.getString(TEXT) }
    private val positive by lazy { arguments?.getInt(POSITIVE) }
    private val negative by lazy { arguments?.getInt(NEGATIVE) }

    private var onButtonClicked: ((DialogButton) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.dialog_base, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(title)
        setIcon(icon)
        if (text != null) {
            if (textid!! > 0)
                setText(textid)
            else
                setText(text)
        }
        setNegative(negative)
        setPositive(positive)
    }

    fun setCanceledOnTouchOutside(cancel: Boolean) {
        super.setCancelable(cancel)
    }

    fun show(hostFragment: Fragment) {
        with(hostFragment) {
            val oldDialog = childFragmentManager.findFragmentByTag(name)
            if (oldDialog != null) return

            show(childFragmentManager, name)
        }
    }

    fun show(hostActivity: AppCompatActivity) {
        with(hostActivity) {
            val oldDialog = supportFragmentManager.findFragmentByTag(name)
            if (oldDialog != null) return

            show(supportFragmentManager, name)
        }
    }

    private fun setTitle(textResId: Int?) {
        dialog_title.isVisible = !resIsNull(textResId)
        if (resIsNull(textResId)) return

        dialog_title.text = getString(textResId!!)
    }

    private fun setIcon(iconResId: Int?) {
        dialog_icon.isVisible = !resIsNull(iconResId)
        if (resIsNull(iconResId)) return

        dialog_icon.setIcon(iconResId!!)
    }

    private fun setText(textResId: Int?) {
        dialog_text.isVisible = !resIsNull(textResId)
        if (resIsNull(textResId)) return

        dialog_text.text = getString(textResId!!)
    }

    private fun setText(text: String?) {
        if (text != null) {
            dialog_text.isVisible = true
            dialog_text.text = text
        }
    }

    private fun setNegative(textResId: Int?) {
        dialog_negative.isInvisible = resIsNull(textResId)
        if (resIsNull(textResId)) return

        dialog_negative.apply {
            text = getString(textResId!!)
            setOnClickListener {
                close()
                onButtonClicked?.invoke(DialogButton.Negative)
            }
        }
    }

    private fun setPositive(textResId: Int?) {
        dialog_positive.isVisible = !resIsNull(textResId)
        if (resIsNull(textResId)) return

        dialog_positive.apply {
            text = getString(textResId!!)
            setOnClickListener {
                close()
                onButtonClicked?.invoke(DialogButton.Positive)
            }
        }

    }

    private fun close() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .remove(this)
            .commitAllowingStateLoss()
    }

    private fun resIsNull(resId: Int?) = resId == null || resId == 0

}