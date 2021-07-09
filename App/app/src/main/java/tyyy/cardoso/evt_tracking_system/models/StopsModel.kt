package tyyy.cardoso.evt_tracking_system.models

data class StopsModel(
    val stops: List<Stop>
) {
    data class Stop(
        val id: String,
        val lat: String,
        val long: String,
        val name: String
    )
}