package tyyy.cardoso.evt_tracking_system.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import tyyy.cardoso.evt_tracking_system.models.StopsTrackEntityModel

interface StopsTrackEntityService {
    @GET("stopstrackedentity/search.php")
    fun getInfo(
        @Query("teid") teid: Int
    ) : Call<StopsTrackEntityModel>
}