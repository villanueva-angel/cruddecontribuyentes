package com.pantherhm.cruddecontribuyentes.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import viewModel.StateListViewModel
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.dp

@Composable
fun StateListScreen(navController: NavController, viewModel: StateListViewModel) {
    LazyColumn {
        items(viewModel.states){ item ->
            Text(
                text = item.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        navController.navigate("detalleestado/${item.nombre}")
                    }
            )
        }
    }
}