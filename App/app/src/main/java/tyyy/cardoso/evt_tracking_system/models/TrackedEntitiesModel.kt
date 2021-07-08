package tyyy.cardoso.evt_tracking_system.models

data class TrackedEntitiesModel(
    val tentity: List<TrackedEntity>
) {
    data class TrackedEntity(
        val ID: String,
        val name: String,
        val description: String,
        val gpsid: String
    )
}