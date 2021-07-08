package tyyy.cardoso.evt_tracking_system.network

import retrofit2.Call
import retrofit2.http.GET
import tyyy.cardoso.evt_tracking_system.models.TrackedEntitiesModel

interface TrackedEntitiesService {
    @GET("trackedentity/read.php")
    fun getInfo() : Call<TrackedEntitiesModel>
}