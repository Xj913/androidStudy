package example.location

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.GnssStatus
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.xiajun.app.MyApp.databinding.LocationActivityBinding
import com.xiajun.base.BaseTitleBarActivity

class LocationActivity : BaseTitleBarActivity() {
    lateinit var bd: LocationActivityBinding
    lateinit var lm: LocationManager
    private var gpsListener: MyLocationListener? = null
    private var networkListner: MyLocationListener? = null
    private var mGnssStatusCallback: MyGnssStatusCallback? = null

    override fun onCreate(arg0: Bundle?) {
        super.onCreate(arg0)
        bd = LocationActivityBinding.inflate(layoutInflater)
        setContentView(bd.getRoot())
        bd.btnStartLocation.setOnClickListener {
            val res = ActivityResultContracts.RequestMultiplePermissions().getSynchronousResult(this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION)
            )
            res?.let { checkLocationProvider() }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                mRequestLocation.launch(
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                )
            } else {
                mRequestLocation.launch(
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                )
            }
        }
        lm = getSystemService(LOCATION_SERVICE) as LocationManager
    }

    private fun checkLocationProvider() {
        val isNetwork = this.isNetworkEnabled
        val isGps = this.isGpsEnabled
        if (!isGps) {
            showToast("开启gps以便获取定位")
            openGPSSetting()
        } else {
            initLocation()
        }
    }

    private fun initLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            mGnssStatusCallback = MyGnssStatusCallback()
            lm.registerGnssStatusCallback(mGnssStatusCallback!!, null)
            networkListner = MyLocationListener(LocationManager.NETWORK_PROVIDER)
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 10f, networkListner!!)
            gpsListener = MyLocationListener(LocationManager.GPS_PROVIDER)
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10f, gpsListener!!)
        }
    }

    inner class MyGnssStatusCallback : GnssStatus.Callback() {
        override fun onStarted() {
            logE(getTAG(), "onStarted")
        }

        override fun onStopped() {
            logE(getTAG(), "onStopped")
        }

        override fun onFirstFix(ttffMillis: Int) {
            logE(getTAG(), "onFirstFix")
        }

        override fun onSatelliteStatusChanged(status: GnssStatus) {
            logE(getTAG(), "onSatelliteStatusChanged SatelliteCount " + status.satelliteCount)
        }
    }

    inner class MyLocationListener(private val provider: String?) : LocationListener {
        override fun onLocationChanged(location: Location) {
            val accuracy = location.accuracy
            val latitude = location.latitude
            val longitude = location.longitude
            logE("onLocationChanged", "latitude:$latitude,longitude:$longitude")
            refreshLocation(location)
        }

        override fun onProviderEnabled(provider: String) {
            logE(this.provider, "onProviderEnabled $provider")
        }

        override fun onProviderDisabled(provider: String) {
            logE(this.provider, "onProviderDisabled $provider")
        }
    }

    private fun refreshLocation(location: Location) {
        runOnUiThread { bd.tvData.text = location.toString() }
    }

    private val isNetworkEnabled: Boolean
        get() {
            val isOpen: Boolean = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            return isOpen
        }

    private val isGpsEnabled: Boolean
        get() {
            val isOpen: Boolean = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
            return isOpen
        }

    private fun openGPSSetting() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
    }

    override fun onDestroy() {
        if (mGnssStatusCallback != null) {
            lm.unregisterGnssStatusCallback(mGnssStatusCallback!!)
        }
        if (networkListner != null) {
            lm.removeUpdates(networkListner!!)
        }
        if (gpsListener != null) {
            lm.removeUpdates(gpsListener!!)
        }
        super.onDestroy()
    }

    var mRequestLocation = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()
    ) { it ->
        val allGranted = it.entries.all { it.value }
        if (allGranted) {
            checkLocationProvider()
        } else {
            showToast("请打开位置信息权限")
        }
    }
}
