package tyyy.cardoso.evt_tracking_system.models

data class BusModel(
    val gps: List<Bus>
) {
    data class Bus(
        val id: Int,
        val lat: Double,
        val long: Double,
        val name: String,
        val status: String
    )
}