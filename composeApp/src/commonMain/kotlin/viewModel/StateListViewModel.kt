package viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList


data class Item(val nombre: String, val detalles: List<String>)

class StateListViewModel {
    val states = mutableStateListOf(
        Item("Guanajuato", listOf("Uriangato", "Moroleón", "Irapuato")),
        Item("Jalisco", listOf("Guadalajara", "Zapopan", "Tlaquepaque"))
    )
}