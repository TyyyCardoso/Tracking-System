package tyyy.cardoso.evt_tracking_system.models

data class StopsTrackEntityModel(
    val stopstentity: List<Stop>
) {
    data class Stop(
        val sid: Int,
        val teid: Int,
        val timearrival: String
    )
}