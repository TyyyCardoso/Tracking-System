package tyyy.cardoso.evt_tracking_system.models

class BusSearch : ArrayList<BusSearch.BusSearchItem>(){
    data class BusSearchItem(
        val Description: String,
        val GPSID: String,
        val ID: String,
        val Name: String
    )
}