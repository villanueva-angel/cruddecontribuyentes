package com.pantherhm.cruddecontribuyentes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.Alignment
import com.pantherhm.cruddecontribuyentes.screens.AddEstadoScreen
import com.pantherhm.cruddecontribuyentes.screens.AddMunScreen
import com.pantherhm.cruddecontribuyentes.screens.AddPersona
import com.pantherhm.cruddecontribuyentes.screens.DetalleMun
import com.pantherhm.cruddecontribuyentes.screens.UpdateMun
import com.pantherhm.cruddecontribuyentes.screens.UpdateState


@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    AppNavHost(navController = navController)
}

@Composable
fun AppNavHost(navController: NavHostController) {

    Box(modifier = Modifier.fillMaxSize())
    {

        val viewmodel = remember { StateListViewModel() }

        NavHost(navController, startDestination = "listaEstados") {
            composable("listaEstados") {
                StateListScreen(navController, viewmodel)
            }
            composable("detalleestado/{nombreEstado}") { backStackEntry ->
                val nombreEstado = backStackEntry.savedStateHandle.get<String>("nombreEstado") ?: ""
                ListaMunicipios(nombreEstado, viewmodel, navController)
            }
            composable("addestado")
            {
                AddEstadoScreen(viewmodel)
            }
            composable("updateestado/{nombreEstado}") { backStackEntry ->
                val nombreEstado = backStackEntry.savedStateHandle.get<String>("nombreEstado") ?: ""
                UpdateState(nombreEstado, viewmodel);
            }
            composable("addmun/{nombreEstado}") { backStackEntry ->
                val nombreEstado = backStackEntry.savedStateHandle.get<String>("nombreEstado") ?: ""
                AddMunScreen(nombreEstado, viewmodel)
            }
            composable("updatemun/{nombreEstado}/{nombreMun}") {
                backStackEntry ->
                val nombreEstado = backStackEntry.savedStateHandle.get<String>("nombreEstado") ?: ""
                val nombreMun = backStackEntry.savedStateHandle.get<String>("nombreMun") ?: ""
                UpdateMun(nombreEstado, nombreMun, viewmodel)
            }
            composable("detallemun/{nombreEstado}/{nombreMun}")
            {
                backStackEntry ->
                val nombreEstado = backStackEntry.savedStateHandle.get<String>("nombreEstado") ?: ""
                val nombreMun = backStackEntry.savedStateHandle.get<String>("nombreMun") ?: ""
                DetalleMun(nombreEstado, nombreMun, viewmodel, navController)
            }
            composable ("addpersona/{nombreEstado}/{nombreMun}") {
                backStackEntry ->
                val nombreEstado = backStackEntry.savedStateHandle.get<String>("nombreEstado") ?: ""
                val nombreMun = backStackEntry.savedStateHandle.get<String>("nombreMun") ?: ""
                AddPersona(nombreEstado, nombreMun, viewmodel)
            }
        }

        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        if (currentRoute != "listaEstados") {
            Button(
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 25.dp)
                    .align(Alignment.BottomStart),
                onClick = { navController.popBackStack() }
            ) {  Text("Volver") }
        }
    }
}