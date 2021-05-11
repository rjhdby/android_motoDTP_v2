package motocitizen.presentation.screens.root

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Point
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.security.KeyChain
import android.security.KeyChainAliasCallback
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.NavController.OnDestinationChangedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import motocitizen.app.push.NOTIFICATION_ACCIDENT_ID_KEY
import motocitizen.app.push.NOTIFICATION_ACCIDENT_NAME_KEY
import motocitizen.main.R
import motocitizen.presentation.base.isVisibleWithAnimation
import motocitizen.presentation.base.setupWithNavController
import motocitizen.presentation.base.viewmodel.VMActivity
import motocitizen.presentation.base.viewmodel.commands.VMCommand

//import motocitizen.presentation.screens.accident.AccidentFragmentArgs

@AndroidEntryPoint
class RootActivity : VMActivity<RootViewModel>(), KeyChainAliasCallback {

    private val REQST_CODE = 100
    private var currentNavController: LiveData<NavController>? = null

    private val destinationChangedListener = OnDestinationChangedListener { _, destination, _ ->
        bottom_navigation.isVisibleWithAnimation =
            bottom_navigation.menu.size() > 1 && destination.id in listOf(
                R.id.homeFragment,
                R.id.mapFragment,
            )
    }

//    private fun checkLocationPermission() {
//        val permissionStatus = ContextCompat.checkSelfPermission(
//            this,
//            Manifest.permission.ACCESS_FINE_LOCATION
//        )
//
//        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
//            startLocationUpdate()
//        } else {
//            if (Build.VERSION.SDK_INT >= 23) {
//                requestPermissions(
//                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                    REQST_CODE
//                )
//            } else {
//                ActivityCompat.requestPermissions(
//                    this,
//                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                    REQST_CODE
//                )
//            }
//        }
//    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        when (requestCode) {
//            REQST_CODE -> {
//                if ((grantResults.isNotEmpty() &&
//                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
//                ) {
//                    startLocationUpdate()
//                }
//            }
//        }
//    }

//    private fun startLocationUpdate() {
//        if (isGpsEnable()) {
//            viewModel.starLocationUpdate()
//        } else {
//            viewModel.starLocationUpdate()
//        }
//
//    }

    override val viewModel: RootViewModel by viewModels()

    override val layoutResource: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        if (savedInstanceState == null) {
            initBottomNavigation()
        } // Else, need to wait for onRestoreInstanceState
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val accidentId = intent?.getStringExtra(NOTIFICATION_ACCIDENT_ID_KEY)
        val accidentName = intent?.getStringExtra(NOTIFICATION_ACCIDENT_NAME_KEY)
        if (accidentId != null && accidentName != null) {
            //val args = AccidentFragmentArgs(accidentId, accidentName).toBundle()
            //currentNavController?.value?.navigate(R.id.accidentFragment, args)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        initBottomNavigation()
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun initBottomNavigation() {
        val navGraphIds = listOf(
            R.navigation.home,
            R.navigation.create_accident,
            R.navigation.map,
        )

        // Setup the bottom navigation view with a list of navigation graphs
        currentNavController = bottom_navigation.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent,
            // unselectableIds = listOf(R.id.accident) ToDo: Soon to be developed feature
        )/* { id ->
            //ToDo: Impl
        }*/

        currentNavController?.observe(this) {
            it.addOnDestinationChangedListener(destinationChangedListener)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        currentNavController?.value?.removeOnDestinationChangedListener(destinationChangedListener)
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    @SuppressLint("RestrictedApi")
    private fun initViews() {
        /*val bottomNavigationMenuView: BottomNavigationMenuView =
            bottom_navigation.getChildAt(0) as BottomNavigationMenuView
        val accidentItemView: BottomNavigationItemView =
            bottomNavigationMenuView.getChildAt(ACCIDENT_ITEM_VIEW_POSITION) as BottomNavigationItemView
        accidentItemView.apply {
            val colorStateList = ColorStateList.valueOf(getColor(R.color.colorRed))
            setIconTintList(colorStateList)
            setTextColor(colorStateList)  ToDo: Soon to be developed feature
        }*/
    }

    override fun onStart() {
        super.onStart()
//        checkLocationPermission()
    }

    override fun initViewModel() {
        super.initViewModel()

        val locationManager =
            this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        viewModel.onAfterInit(locationManager)

        bottom_navigation.menu.clear()
        bottom_navigation.menu.add(
            Menu.NONE,
            R.id.home,
            Menu.NONE,
            getString(R.string.home)
        ).setIcon(R.drawable.ic_home)

        bottom_navigation.menu.add(
            Menu.NONE,
            R.id.create_accident,
            Menu.NONE,
            getString(R.string.activity_main_add_point_button)
        ).setIcon(R.drawable.create)

        bottom_navigation.menu.add(
            Menu.NONE,
            R.id.map,
            Menu.NONE,
            getString(R.string.tab_map)
        ).setIcon(R.drawable.ic_map_white_48dp)
        // ToDo: Add more items
        initBottomNavigation()
    }

    override fun onCommandReceived(command: VMCommand) {
        super.onCommandReceived(command)
        when (command) {
            ForceChooseCertificate -> viewModel.forceChooseCertificate()
            ChooseCertificate -> chooseCert()
        }
    }

    private fun chooseCert() {
        KeyChain.choosePrivateKeyAlias(
            this, this, arrayOf(),  // Any key types.
            null,  // Any issuers.
            null,  // Any host
            -1,  // Any port
            null
        )
    }

    override fun alias(alias: String?) {
        viewModel.onAliasChosen(alias)
    }

    fun toHome(){
        val view: View = bottom_navigation.findViewById(R.id.home)
        view.performClick()
    }

    fun isGpsEnable(): Boolean {
        val locationManager =
            this.getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }


}