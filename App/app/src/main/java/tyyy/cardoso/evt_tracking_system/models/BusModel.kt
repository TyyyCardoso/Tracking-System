package tyyy.cardoso.evt_tracking_system.models

data class BusModel(
    val gps: List<Bus>
) {
    data class Bus(
        val id: String,
        val lat: String,
        val long: String,
        val name: String,
        val status: String
    )
}