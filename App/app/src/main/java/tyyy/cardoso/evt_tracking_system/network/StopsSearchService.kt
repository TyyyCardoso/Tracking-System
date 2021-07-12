package tyyy.cardoso.evt_tracking_system.network

import retrofit2.Call
import retrofit2.http.GET
import tyyy.cardoso.evt_tracking_system.models.StopsModel

interface StopsSearchService {
    @GET("stops/read.php")
    fun getInfo(
    ) : Call<StopsModel>
}