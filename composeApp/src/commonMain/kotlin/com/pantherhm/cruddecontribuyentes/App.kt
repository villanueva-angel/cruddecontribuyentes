package com.pantherhm.cruddecontribuyentes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pantherhm.cruddecontribuyentes.screens.ListaMunicipios
import com.pantherhm.cruddecontribuyentes.screens.StateListScreen
import androidx.navigation.compose.rememberNavController
import viewModel.StateListViewModel
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import javax.swing.Box


@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    AppNavHost(navController = navController)
}

@Composable
fun AppNavHost(navController: NavHostController) {
        val viewmodel = remember { StateListViewModel() }
        NavHost(navController, startDestination = "listaEstados") {
            composable("listaEstados") {
                StateListScreen(navController, viewmodel)
            }
            composable("detalleestado/{nombreEstado}") { backStackEntry ->
                val nombreEstado = backStackEntry.savedStateHandle.get<String>("nombreEstado") ?: ""
                ListaMunicipios(nombreEstado, viewmodel)
            }
        }
}