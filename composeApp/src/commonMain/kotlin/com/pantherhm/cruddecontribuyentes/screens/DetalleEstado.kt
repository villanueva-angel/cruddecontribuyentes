package com.pantherhm.cruddecontribuyentes.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import viewModel.StateListViewModel
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.awt.Button
import  androidx.compose.material3.Button
@Composable
fun ListaMunicipios(nombreEstado: String, viewModel: StateListViewModel) {
    val estado = viewModel.states.find { it.nombre == nombreEstado }

    Column {
        Text(text = "Municipios de $nombreEstado", style = MaterialTheme.typography.titleLarge)
        LazyColumn {
            items(estado?.detalles ?: emptyList<String>()) { municipio ->
                Text(
                    text = municipio,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                )
            }
        }
    }
    if (estado == null) Text("No hay municipios")

}