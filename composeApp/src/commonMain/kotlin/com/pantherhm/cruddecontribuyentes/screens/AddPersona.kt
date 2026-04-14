package com.pantherhm.cruddecontribuyentes.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import viewModel.StateListViewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import viewModel.DomicilioFiscal
import viewModel.Persona
import viewModel.PersonaFisica
import viewModel.PersonaMoral


@Composable
fun AddPersona(estadoNombre: String, municipio: String, viewModel : StateListViewModel)
{
    var added by remember { mutableStateOf(false) }
    var attemped by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var curp by remember { mutableStateOf("") }
    var fechaNac by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var tel by remember { mutableStateOf("") }

    var rfc by remember { mutableStateOf("") }
    var razonSocial by remember { mutableStateOf("") }
    var rfcsocio by remember { mutableStateOf("") }
    var regimenCapital by remember { mutableStateOf("General") }
    var actEconomica by remember { mutableStateOf("Agropecuario y extractivo") }

    var tipoPersona by remember { mutableStateOf("Persona Fisica") }

    var cp by remember { mutableStateOf("") }
    var localidad by remember { mutableStateOf("") }
    var colonia by remember { mutableStateOf("") }
    var vialidad by remember {mutableStateOf("Calle")}
    var nombreVialidad by remember { mutableStateOf("") }
    var numeroExterior by remember { mutableStateOf(0) }
    var numeroInterior by remember { mutableStateOf(0) }
    var entrCalle1 by remember { mutableStateOf("") }
    var entrCalle2 by remember { mutableStateOf("") }
    var refAdd by remember { mutableStateOf("") }
    var caracteristicas by remember { mutableStateOf("") }
    var regimenFiscal by remember { mutableStateOf("General de ley") }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.align(Alignment.Center)
        ) {
            item {
                DropdownMenuBox(
                    options = listOf("Persona Física", "Persona Moral"),
                    selectedOption = tipoPersona,
                    onOptionSelected = { tipoPersona = it }
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                if (tipoPersona == "Persona Fisica") {
                    Column {
                        TextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Nombre Completo") },
                            singleLine = true
                        )
                        TextField(
                            value = curp,
                            onValueChange = { curp = it },
                            label = { Text("CURP") },
                            singleLine = true
                        )
                        TextField(
                            value = fechaNac,
                            onValueChange = { fechaNac = it },
                            label = { Text("Fecha de Nacimiento") },
                            singleLine = true
                        )
                        TextField(
                            value = correo,
                            onValueChange = { correo = it },
                            label = { Text("Correo Electrónico") },
                            singleLine = true
                        )
                        TextField(
                            value = tel,
                            onValueChange = { tel = it },
                            label = { Text("Teléfono") },
                            singleLine = true
                        )
                    }
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        TextField(
                            value = razonSocial,
                            onValueChange = { razonSocial = it },
                            label = { Text("Razón Social") }
                        )
                        TextField(
                            value = rfc,
                            onValueChange = { rfc = it },
                            label = { Text("RFC representante") },
                            singleLine = true
                        )
                        TextField(
                            value = rfcsocio,
                            onValueChange = { rfcsocio = it },
                            label = { Text("RFC socio") },
                            singleLine = true
                        )
                        TextField(
                            value = fechaNac,
                            onValueChange = { fechaNac = it },
                            label = { Text("Fecha const") },
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Box {
                            Text("Regimen Capital")
                            DropdownMenuBox(
                                options = listOf(
                                    "General",
                                    "No lucrativos",
                                    "PEMEX",
                                    "Residentes en el extranjero"
                                ),
                                selectedOption = regimenCapital,
                                onOptionSelected = { regimenCapital = it },
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Domicilio Fiscal")
                Spacer(modifier = Modifier.height(16.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    TextField(
                        value = cp,
                        onValueChange = { cp = it },
                        label = { Text("Codigo Postal") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                    TextField(
                        value = localidad,
                        onValueChange = { localidad = it },
                        label = { Text("Localidad") },
                        singleLine = true
                    )
                    TextField(
                        value = colonia,
                        onValueChange = { colonia = it },
                        label = { Text("Colonia") },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Box {
                        Text("Vialidad")
                        DropdownMenuBox(
                            options = listOf(
                                "Calle",
                                "Avenida",
                                "Carretera",
                                "Autopista",
                                "Andador",
                                "Ampliacion",
                                "Boulevard",
                                "Privada",
                                "Prolongacion",
                                "Calzada",
                                "Circuito",
                                "Diagonal",
                                "Retorno",
                                "Acuatica"
                            ),
                            selectedOption = vialidad,
                            onOptionSelected = { vialidad = it },
                        )
                    }
                    TextField(
                        value = numeroExterior.toString(),
                        onValueChange = { numeroExterior = it.toInt() },
                        label = { Text("Numero exterior") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    TextField(
                        value = numeroInterior.toString(),
                        onValueChange = { numeroInterior = it.toInt() },
                        label = { Text("Numero interior") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    TextField(
                        value = entrCalle1,
                        onValueChange = { entrCalle1 = it },
                        label = { Text("Entre calle 1") },
                        singleLine = true
                    )
                    TextField(
                        value = entrCalle2,
                        onValueChange = { entrCalle2 = it },
                        label = { Text("Entre calle 2") },
                        singleLine = true
                    )
                    TextField(
                        value = refAdd,
                        onValueChange = { refAdd = it },
                        label = { Text("Referencia Adicional") },
                        singleLine = true
                    )
                    TextField(
                        value = caracteristicas,
                        onValueChange = { caracteristicas = it },
                        label = { Text("Caracteristicas") },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Box {
                        Text("Actividad Economica")
                        DropdownMenuBox(
                            options = listOf(
                                "Agropecuario y extractivo",
                                "Industrial",
                                "Servicios y comercio",
                                "Actividades gubernamentales",
                                "Asociaciones sin fines de lucro",
                                "Actividades internacionales"
                            ),
                            selectedOption = actEconomica,
                            onOptionSelected = { actEconomica = it },
                        )
                    }
                    if(tipoPersona == "Persona Moral")
                    {
                        Box {
                            Text("Regimen Fiscal")
                            DropdownMenuBox(
                                options = listOf(
                                    "General de ley",
                                    "Simplificado de ley",
                                    "Personas morales con fines no lucrativos",
                                    "Consolidacion y recidentes en el extranjero"
                                ),
                                selectedOption = regimenFiscal,
                                onOptionSelected = { regimenFiscal = it },
                            )
                        }
                    }
                    else
                    {
                        Box {
                            Text("Regimen Fiscal")
                            DropdownMenuBox(
                                options = listOf(
                                    "Regimen Simplificado",
                                    "Sueldos y salarios e ingresos asimilados",
                                    "Arrendamiento",
                                    "Actividades empresariales y profesionales",
                                    "Plataformas tecnologicas",
                                    "Enajenacion de bienes",
                                    "Ingresos por intereses"
                                ),
                                selectedOption = regimenFiscal,
                                onOptionSelected = { regimenFiscal = it },
                            )
                        }
                    }

                }
            }

            item {
                //No tengo tiempo de validar tantos datos _ n _

                Button(
                    onClick = {
                        var nombreV = when{
                            vialidad.equals("Avenida") -> "Av"
                            vialidad.equals("Boulevard") -> "Bvo"
                            else -> vialidad
                        } + "."
                        var domicilioFiscal = DomicilioFiscal(cp, estadoNombre, municipio, localidad, colonia, vialidad,
                            nombreV, numeroExterior, numeroInterior, entrCalle1,
                            entrCalle2, refAdd, caracteristicas,
                            actEconomica, regimenFiscal)
                        var persona = Persona(domicilioFiscal)

                        if(tipoPersona == "Persona Fisica")
                        {
                            persona = PersonaFisica(domicilioFiscal, curp.uppercase(), name, fechaNac, correo,
                                tel)
                        }
                        else
                        {
                            persona = PersonaMoral(domicilioFiscal, razonSocial, fechaNac, rfc.uppercase(),
                                rfcsocio, viewModel.states.size, regimenCapital)
                        }

                        added = viewModel.AddPersona(estadoNombre, municipio, persona)
                        attemped = true
                    },
                        Modifier.padding(vertical = 8.dp),
                ) {  Text("Add")  }
            }
            if(attemped )
            {
                item {
                    Text(
                        text = when {
                            added -> "Persona agregadad con exito"
                            else -> "Algo salio mal. Porfavor revice la informacion"
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuBox(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text("Régimen de Capital") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}