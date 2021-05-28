package motocitizen.presentation.screens.settings.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_base.*
import motocitizen.app.App
import motocitizen.domain.model.user.UserRole
import motocitizen.main.R

const val MAX_DEEP_HORS = 24
const val DEEP_MAX_LENGTH = 5
const val RADIUS_MAX_LENGTH = 5

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    lateinit var distance: EditTextPreference
    lateinit var deep: EditTextPreference
    lateinit var exit: Preference
    val viewModel: SettingsViewModel by lazy {
        ViewModelProvider(requireActivity()).get(
            SettingsViewModel::class.java
        )
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.prefference, rootKey)
        initPreference()
        updateUI()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    private fun initPreference() {
        initExitBtn()
        innitDeepPreference()
        innitDistancePreference()
    }

    private fun initExitBtn() {
        exit = findPreference(getString(R.string.settings_exit_btn))!!
        exit.summary = App.user.nick
        exit.setOnPreferenceClickListener {
//            TODO Реадизовать выход из сессии
            true
        }
    }

    private fun innitDistancePreference() {
        distance = findPreference(getString(R.string.settings_distance_key))!!
        distance.apply {
            setOnBindEditTextListener { distanceEditText ->
                val inputLengthFilter = InputFilter.LengthFilter(RADIUS_MAX_LENGTH)
                distanceEditText.filters = arrayOf(inputLengthFilter)
                distanceEditText.inputType = InputType.TYPE_CLASS_NUMBER
            }
        }
    }

    private fun innitDeepPreference() {
        deep = findPreference(getString(R.string.settings_deep_key))!!
        deep.apply {
            setOnBindEditTextListener { deepEditText ->
                val inputLengthFilter = InputFilter.LengthFilter(DEEP_MAX_LENGTH)
                deepEditText.inputType = InputType.TYPE_CLASS_NUMBER
                deepEditText.filters = arrayOf(inputLengthFilter)
                deepEditText.addTextChangedListener {
                    if (it != null && it.isNotEmpty()) {
                        if (it.toString()
                                .toInt() > MAX_DEEP_HORS && (App.user.role == UserRole.USER || App.user.role == UserRole.READ_ONLY)
                        ) {
                            showToast(R.string.max_deep_time)
                            deepEditText.setText(MAX_DEEP_HORS.toString())
                        }
                    }
                }
            }
        }
    }

    private fun showToast(StringId: Int) {
        Toast.makeText(
            this@SettingsFragment.requireContext(),
            getString(StringId),
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            getString(R.string.settings_distance_key) -> {
                if (viewModel.getDistance().isEmpty()) {
                    viewModel.setTempDistance()
                }
            }
            getString(R.string.settings_deep_key) -> {
                if (viewModel.getDeep().isEmpty()) {
                    viewModel.setTempDeep()
                }
            }
        }
        updateUI()
    }

    private fun updateUI() {
        updateDeepSummary()
        updateDistanceSummary()
    }

    private fun updateDeepSummary() {
        val stringBuilder = StringBuilder().append(getString(R.string.show_no_older)).append(
            viewModel.getDeep()
        ).append(getString(R.string.H))
        deep.summary = stringBuilder
    }

    private fun updateDistanceSummary() {
        val stringBuilder = StringBuilder().append(getString(R.string.show_no_further))
            .append(viewModel.getDistance()).append(getString(R.string.kilometer))
        distance.summary = stringBuilder
    }

    override fun onDisplayPreferenceDialog(preference: Preference?) {
        when (preference!!.key) {
            getString(R.string.settings_distance_key) -> {
                viewModel.updateTempDistance()
            }
            getString(R.string.settings_deep_key) -> {
                viewModel.updateTempDeep()
            }
        }
        super.onDisplayPreferenceDialog(preference)
    }
}