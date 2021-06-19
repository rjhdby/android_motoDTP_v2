package motocitizen.presentation.screens.settings.settings

import android.content.Intent
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
import motocitizen.main.R
import motocitizen.presentation.screens.auth.AuthActivity

const val DEPTH_MAX_LENGTH = 5
const val RADIUS_MAX_LENGTH = 5
const val RADIUS_MIN = 1
const val DEPTH_MIN = 1

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var distance: EditTextPreference
    private lateinit var depth: EditTextPreference
    private lateinit var exit: Preference

    val viewModel: SettingsViewModel by lazy {
        ViewModelProvider(requireActivity()).get(
            SettingsViewModel::class.java
        )
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.prefference, rootKey)
        initPreference()
        updateUI()
        initViewModel()
        viewModel.loadUser()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    private fun initPreference() {
        initExitBtn()
        initDepthPreference()
        initDistancePreference()
    }

    private fun initExitBtn() {
        exit = findPreference(getString(R.string.settings_exit_btn))!!
        exit.summary
        exit.setOnPreferenceClickListener {
            viewModel.logOut()
            val intent = Intent(requireContext(), AuthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            requireActivity().finish()
            true
        }
    }

    private fun initDistancePreference() {
        distance = findPreference(getString(R.string.settings_distance_key))!!
        distance.apply {
            setOnBindEditTextListener { distanceEditText ->
                val inputLengthFilter = InputFilter.LengthFilter(RADIUS_MAX_LENGTH)
                distanceEditText.filters = arrayOf(inputLengthFilter)
                distanceEditText.inputType = InputType.TYPE_CLASS_NUMBER
                distanceEditText.addTextChangedListener {
                    if (it != null && it.isNotEmpty())
                        if (it.toString()
                                .toInt() < RADIUS_MIN
                        ) {
                            showToast(R.string.min_radius)
                            distanceEditText.setText(DEPTH_MIN.toString())
                        }
                }
            }
        }
    }

    private fun initDepthPreference() {
        depth = findPreference(getString(R.string.settings_depth_key))!!
        depth.apply {
            setOnBindEditTextListener { depthEditText ->
                val inputLengthFilter = InputFilter.LengthFilter(DEPTH_MAX_LENGTH)
                depthEditText.inputType = InputType.TYPE_CLASS_NUMBER
                depthEditText.filters = arrayOf(inputLengthFilter)
                depthEditText.addTextChangedListener {
                    if (it != null && it.isNotEmpty())
                        if (it.toString()
                                .toInt() < DEPTH_MIN
                        ) {
                            showToast(R.string.min_depth_time)
                            depthEditText.setText(DEPTH_MIN.toString())
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
            getString(R.string.settings_depth_key) -> {
                if (viewModel.getDepth().isEmpty()) {
                    viewModel.setTempDepth()
                }
            }
        }
        updateUI()
    }

    private fun updateUI() {
        updateDepthSummary()
        updateDistanceSummary()
    }

    private fun updateDepthSummary() {
        val stringBuilder = StringBuilder().append(getString(R.string.show_no_older)).append(
            viewModel.getDepth()
        ).append(getString(R.string.hour))
        depth.summary = stringBuilder
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
            getString(R.string.settings_depth_key) -> {
                viewModel.updateTempDepth()
            }
        }
        super.onDisplayPreferenceDialog(preference)
    }

    fun initViewModel() {
        viewModel.checkUserState.observe(this) { checkRestrictionsState ->
            checkRestrictionsState.asContentOrNull()?.let {
                exit.summary = it.nick
            }
        }
    }
}