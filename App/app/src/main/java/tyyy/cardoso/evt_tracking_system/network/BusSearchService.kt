package tyyy.cardoso.evt_tracking_system.network

import retrofit2.Call
import retrofit2.http.GET
import tyyy.cardoso.evt_tracking_system.models.BusModel

interface BusSearchService {
    @GET("gps/read.php")
    fun getInfo() : Call<BusModel>
}