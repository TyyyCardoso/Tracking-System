package tyyy.cardoso.evt_tracking_system.models

data class StopsTrackEntityModel(
    val stops: List<Stop>
) {
    data class Stop(
        val sid: String,
        val teid: String,
        val timearrival: String
    )
}