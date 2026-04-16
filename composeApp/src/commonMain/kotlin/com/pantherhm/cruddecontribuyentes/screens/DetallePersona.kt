package com.pantherhm.cruddecontribuyentes.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import viewModel.PersonaFisica
import viewModel.PersonaMoral
import viewModel.StateListViewModel

@Composable
fun DetallePersona(estN : String, munN : String, tipo : String, identify : String, viewmodel : StateListViewModel)
{
    var estado = viewmodel.states.find { it.nombre.equals(estN) }
    if(estado == null) return
    var mun = estado.municipios.find { it.nombre.equals((munN)) }
    if(mun == null) return
    var persona = mun.contribuidores.find {
        it is PersonaFisica && tipo == "fisica" && it.curp.equals(identify) ||
                it is PersonaMoral && tipo == "moral" && it.rfcRepresentante.equals(identify)
    }
    if(persona == null) return
    Column {
        if (persona is PersonaFisica) {
            Text(
                "Curp: ${persona.curp}\n" +
                        "Nombre completo: ${persona.nombreCompleto}\n" +
                        "Fecha Nac: ${persona.fechaNac}\n" +
                        "Correo elec: ${persona.correoElec}\n" +
                        "Telefono: ${persona.tel}"
            )
        } else if (persona is PersonaMoral) {
            Text(
                "No Poliza: ${persona.noPoliza}\n" +
                        "RFC Representante: ${persona.rfcRepresentante}\n" +
                        "Fecha Const: ${persona.fechaConst}\n" +
                        "RFC Socio: ${persona.rfcSocio}\n" +
                        "Regimen: ${persona.regimenCapital}\n" +
                        "Razon social: ${persona.razonSocial}"
            )
        }
        Text(
            "Codigo Postal: ${persona.domicilioFiscal.cp}\n" +
                    "Localidad: ${persona.domicilioFiscal.localidad}\n" +
                    "Colonia: ${persona.domicilioFiscal.colonia}\n" +
                    "Vialidad: ${persona.domicilioFiscal.vialidad}\n" +
                    "Nombre vialidad: ${persona.domicilioFiscal.nombreVialidad}\n" +
                    "Numero exterior: ${persona.domicilioFiscal.numeroExterior}\n" +
                    "Numero interior: ${persona.domicilioFiscal.numeroInterior}\n" +
                    "Entre calle1: ${persona.domicilioFiscal.entrecalle1}\n" +
                    "Entre calle2: ${persona.domicilioFiscal.entrecalle2}\n" +
                    "Referencia adicional: ${persona.domicilioFiscal.referenciaAdicional}\n" +
                    "Caracteristicas: ${persona.domicilioFiscal.caracteristicas}\n" +
                    "Actividad economica: ${persona.domicilioFiscal.actividadEconomica}\n" +
                    "Regimen fiscal: ${persona.domicilioFiscal.regimenFiscal}"
        )
    }
}