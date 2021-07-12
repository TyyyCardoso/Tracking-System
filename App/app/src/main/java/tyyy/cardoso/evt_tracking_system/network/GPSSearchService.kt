package tyyy.cardoso.evt_tracking_system.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import tyyy.cardoso.evt_tracking_system.models.GPSModel

interface GPSSearchService {
    @GET("gps/search.php")
    fun getInfo(
        @Query("status") status: String
    ) : Call<GPSModel>
}