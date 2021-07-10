package tyyy.cardoso.evt_tracking_system.models

data class StopsModel(
    val stops: List<Stop>
) {
    data class Stop(
        val id: Int,
        val lat: Double,
        val long: Double,
        val name: String
    )
}