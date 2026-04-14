package com.pantherhm.cruddecontribuyentes.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import viewModel.StateListViewModel
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import  androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pantherhm.cruddecontribuyentes.Dialogs.AlterWarning
import viewModel.DomicilioFiscal
import viewModel.Municipio
import viewModel.Persona
import viewModel.PersonaFisica
import viewModel.PersonaMoral

@Composable
fun DetalleMun(nombreEstado : String, nombreMun : String, viewmodel : StateListViewModel, navController: NavHostController)
{
    val estado = viewmodel.states.find { it.nombre.equals(nombreEstado) }
    val mun = estado?.municipios?.find { it.nombre.equals(nombreMun) }
    if(estado == null || mun == null) return
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        var search by remember { mutableStateOf("") }
        val fs1 = (maxWidth.value * 0.05f).sp
        val fss1 = (maxWidth.value * 0.02f).sp
        val fs2 = (maxWidth.value * 0.025f).sp
        val fss2 = (maxWidth.value * 0.015f).sp
        val columns = when {
            maxWidth < 600.dp -> 1
            maxWidth < 800.dp -> 2
            else -> 3
        }
        var showDialog by remember { mutableStateOf(false) }
        var persona by remember { mutableStateOf(Persona(DomicilioFiscal("","","",
            "","","","", 0,0,"",
            "","","","",""))) }

        Column(modifier = Modifier.fillMaxSize().padding(start = 16.dp, end = 16.dp).border(5.dp, Color.Black, RectangleShape)) {
            TextField(
                value = search,
                onValueChange = { search = it },
                label = { Text("Rfc del representante o curp de la persona fisica") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp))

            val filtered = mun.contribuidores.filter {
                        it is PersonaMoral && it.rfcRepresentante.contains(search) ||
                        it is PersonaFisica && it.curp.contains(search)
            }

            //Mensaje de resultados
            if(filtered.isEmpty()){
                Text(
                    text = when {
                        mun.contribuidores.isEmpty() -> "No hay nadie inscrito aun"
                        else -> "No hay resultados"
                    },
                    modifier = Modifier
                        .fillMaxHeight(0.2f)
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = fs1
                    ))
                return@Column
            }

            //Lista de estados
            if (columns == 1) {
                LazyColumn {
                    items(filtered){ item ->
                        val identify = when {
                            item is PersonaFisica -> item.curp
                            item is PersonaMoral -> item.rfcRepresentante
                            else -> "No identificado"
                        }
                        val tipo = when{
                            item is PersonaFisica -> "fisica"
                            item is PersonaMoral -> "moral"
                            else -> "NAT"
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Button(
                                onClick = { navController.navigate("detallepersona/${nombreEstado}/${mun}/${tipo}/${identify}") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RectangleShape
                            ) {
                                Text(
                                    text = identify,
                                    modifier = Modifier
                                        .padding(16.dp),
                                    textAlign = TextAlign.Center,
                                    style = TextStyle(fontSize = fs1)
                                )
                            }
                            Column(modifier = Modifier
                                .align(Alignment.TopEnd)){
                                Button(
                                    onClick = { showDialog = true; persona = item },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Red,
                                        contentColor = Color.White
                                    )
                                ) {  Text("Delete", style = TextStyle(fontSize = fss1))  }
                                Button(
                                    onClick = { navController.navigate("updatepersona/${nombreEstado}/${nombreMun}/${tipo}/${identify}") },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Blue,
                                        contentColor = Color.White
                                    )
                                ) {  Text("Update", style = TextStyle(fontSize = fss1))  }
                            }
                        }
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(columns),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(items = filtered) { item ->
                        val identify = when {
                            item is PersonaFisica -> item.curp
                            item is PersonaMoral -> item.rfcRepresentante
                            else -> "No identificado"
                        }
                        val tipo = when{
                            item is PersonaFisica -> "fisica"
                            item is PersonaMoral -> "moral"
                            else -> "NAT"
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Button(
                                onClick = { navController.navigate("detallepersona/${nombreEstado}/${nombreMun}/${tipo}/${identify}") },
                                modifier = Modifier.padding(8.dp),
                                shape = RectangleShape
                            ) {
                                Text(
                                    text = identify,
                                    textAlign = TextAlign.Center,
                                    style = TextStyle(fontSize = fs2),
                                    modifier = Modifier
                                        .fillMaxHeight(0.1f)
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                )
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                Button(
                                    onClick = { showDialog = true; persona = item },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Red,
                                        contentColor = Color.White
                                    )
                                ) {  Text("Delete", style = TextStyle(fontSize = fss2))  }
                                Button(
                                    onClick = { navController.navigate("updatepersona/${nombreEstado}/${nombreMun}/${tipo}/${identify}") },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Blue,
                                        contentColor = Color.White
                                    )
                                ) {  Text("Update", style = TextStyle(fontSize = fss2))  }
                            }
                        }
                    }
                }
            }
        }

        Button(onClick = { navController.navigate("addpersona/${nombreEstado}/${nombreMun}") } ,
            colors = ButtonDefaults.buttonColors(Color.Green, Color.White),
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 25.dp)
                .align(when{
                    columns == 1 -> Alignment.TopEnd
                    else -> Alignment.BottomEnd
                })
        )
        {  Text("Add")  }

        if (showDialog) {
            val identify = when {
                persona is PersonaFisica -> (persona as PersonaFisica).curp
                persona is PersonaMoral -> (persona as PersonaMoral).rfcRepresentante
                else -> "NAT"
            }
            val tipo = when{
                persona is PersonaFisica -> "fisica"
                persona is PersonaMoral -> "moral"
                else -> "NAT"
            }
            AlterWarning(
                nombre = identify,
                onConfirm = {
                    viewmodel.DeletePersona(nombreEstado, nombreMun, tipo, identify)
                    showDialog = false
                },
                onDismiss = { showDialog = false }
            )
        }
    }
}

