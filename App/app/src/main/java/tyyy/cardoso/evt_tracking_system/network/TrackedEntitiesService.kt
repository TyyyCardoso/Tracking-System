package tyyy.cardoso.evt_tracking_system.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import tyyy.cardoso.evt_tracking_system.models.TrackedEntitiesModel

interface TrackedEntitiesService {
    @GET("trackedentity/read_one.php")
    fun getInfo(
        @Query("gpsid") gpsid : Int
    ) : Call<TrackedEntitiesModel>
}