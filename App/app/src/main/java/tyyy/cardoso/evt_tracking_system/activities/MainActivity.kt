package tyyy.cardoso.evt_tracking_system.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tyyy.cardoso.evt_tracking_system.R
import tyyy.cardoso.evt_tracking_system.constants.Constants
import tyyy.cardoso.evt_tracking_system.models.BusModel
import tyyy.cardoso.evt_tracking_system.models.TrackedEntitiesModel
import tyyy.cardoso.evt_tracking_system.network.BusSearchService
import tyyy.cardoso.evt_tracking_system.network.TrackedEntitiesService


class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var lat : Double = 0.0
    private var long : Double = 0.0
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var busList : BusModel? = null
    private var trackedEntitiesList : TrackedEntitiesModel? = null

    private val latList = arrayListOf<Double>()
    private val longList = arrayListOf<Double>()
    private val busTitles = arrayListOf<String>()
    private val busID = arrayListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         *
         * PUT THE STATUS BAR FOREGROUND COLOR TO BLACK
         *
         * DEPRECATED
         *
         * */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        };//  set status text dark


        /**
         *
         * INITALIZATING THE GOOGLE MAP
         *
         */

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        getBusSearch()
    }

    private fun getBusSearch() {
        /**
         * Add the built-in converter factory first. This prevents overriding its
         * behavior but also ensures correct behavior when using converters that consume all types.
         */
        val retrofit: Retrofit = Retrofit.Builder()
            // API base URL.
            .baseUrl(Constants.BASE_URL)
            /** Add converter factory for serialization and deserialization of objects. */
            /**
             * Create an instance using a default {@link Gson} instance for conversion. Encoding to JSON and
             * decoding from JSON (when no charset is specified by a header) will use UTF-8.
             */
            .addConverterFactory(GsonConverterFactory.create())
            /** Create the Retrofit instances. */
            .build()

        /**
         * Here we map the service interface in which we declares the end point and the API type
         *i.e GET, POST and so on along with the request parameter which are required.
         */
        val service: BusSearchService =
            retrofit.create<BusSearchService>(BusSearchService::class.java)

        /** An invocation of a Retrofit method that sends a request to a web-server and returns a response.
         * Here we pass the required param in the service
         */
        val listCall: Call<BusModel> = service.getInfo("on")

        // Callback methods are executed using the Retrofit callback executor.
        listCall.enqueue(object : Callback<BusModel> {
            override fun onResponse(
                call: Call<BusModel>,
                response: Response<BusModel>
            ) {
                // Check the response is success or not.
                if (response!!.isSuccessful) {
                    busList = response.body()
                    getTrackedEntitiesSearch()
                    Log.i("Bus1", "$busList")
                }
            }

            override fun onFailure(call: Call<BusModel>, t: Throwable) {
                Log.i("Errrrror", "$t")
            }

        })
    }

    private fun getTrackedEntitiesSearch() {
        /**
         * Add the built-in converter factory first. This prevents overriding its
         * behavior but also ensures correct behavior when using converters that consume all types.
         */
        val retrofit: Retrofit = Retrofit.Builder()
            // API base URL.
            .baseUrl(Constants.BASE_URL)
            /** Add converter factory for serialization and deserialization of objects. */
            /**
             * Create an instance using a default {@link Gson} instance for conversion. Encoding to JSON and
             * decoding from JSON (when no charset is specified by a header) will use UTF-8.
             */
            .addConverterFactory(GsonConverterFactory.create())
            /** Create the Retrofit instances. */
            .build()

        /**
         * Here we map the service interface in which we declares the end point and the API type
         *i.e GET, POST and so on along with the request parameter which are required.
         */
        val service: TrackedEntitiesService =
            retrofit.create<TrackedEntitiesService>(TrackedEntitiesService::class.java)

        /** An invocation of a Retrofit method that sends a request to a web-server and returns a response.
         * Here we pass the required param in the service
         */
        for(item in busList!!.gps) {
                //Log.i("id", "${item.id}")
                val listCall: Call<TrackedEntitiesModel> = service.getInfo(item.id)

                // Callback methods are executed using the Retrofit callback executor.
                listCall.enqueue(object : Callback<TrackedEntitiesModel> {
                    override fun onResponse(
                        call: Call<TrackedEntitiesModel>,
                        response: Response<TrackedEntitiesModel>
                    ) {
                        // Check the response is success or not.
                        if (response!!.isSuccessful) {
                            trackedEntitiesList = response.body()
                            busTitles.add(trackedEntitiesList!!.name)
                            busID.add(trackedEntitiesList!!.gpsid)
                            Log.i("Bus1", "$trackedEntitiesList")
                            Log.i("name", "${trackedEntitiesList!!.name}")
                            Log.i("SIZE1", "${busTitles.size}")

                        }
                    }

                    override fun onFailure(call: Call<TrackedEntitiesModel>, t: Throwable) {
                        Log.i("Errrrror", "$t")
                    }

                })
       }
        Handler(Looper.getMainLooper()).postDelayed({
            addMarkers(busTitles)
        }, 1000)

    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        if(Constants.isLocationAvailable(this)){
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        lat = location.latitude
                        long = location.longitude

                        val currentLocation = LatLng(lat, long)
                        var zoomLevel = 15.0f
                        mMap.addMarker(MarkerOptions().position(currentLocation).title("You").icon(bitmapDescriptorFromVector(this,
                            R.drawable.ic_baseline_person_outline_24
                        )))
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,zoomLevel))
                    }
                }
        }else{
            Constants.isLocationAvailable(this)
        }
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

    private fun addMarkers(titles : ArrayList<String>){

        //Log.i("GPS1ID", "${busList!!.gps[1].id}")

        for(i in 0 until busID.size){
            for(j in 0 until busList!!.gps.size){
                //Log.i("ORANGO[I]", "${busID[i]}")
                //Log.i("ORANGO[j]", "${busList!!.gps[j].id}")
                if(busID[i]==busList!!.gps[j].id){
                    latList!!.add(busList!!.gps[j].lat)
                    longList!!.add(busList!!.gps[j].long)
                    break
                }
                //Log.i("GPS1ID", "${busList!!.gps[i].id}")
                //Log.i("BUS1ID", "${busID[i]}")
            }

        }


        for(i in 0 until latList!!.size){
            val busLocation = LatLng(latList!![i], longList!![i])
            mMap.addMarker(MarkerOptions().position(busLocation).title(busTitles[i]).icon(bitmapDescriptorFromVector(this,
                R.drawable.ic_baseline_directions_bus_24
            )))
        }
    }

}
