package viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import cruddecontribuyentes.composeapp.generated.resources.Res


data class Estado(val id : Int, val nombre: String, val nab : String, var municipios: MutableList<Municipio>)
data class Municipio(val id : Int, val nombre : String, val nab : String, var contribuidores: MutableList<Persona>)
open class Persona(val domicilioFiscal: DomicilioFiscal)
class PersonaFisica (
    domicilioFiscal: DomicilioFiscal,
    val curp : String,
    val nombreCompleto : String,
    val fechaNac : String,
    val correoElec : String,
    val tel : String,
) : Persona(domicilioFiscal)

class PersonaMoral(
    domicilioFiscal: DomicilioFiscal,
    val razonSocial : String,
    val fechaConst : String,
    val rfcRepresentante : String,
    val rfcSocio : String,
    val noPoliza : Int,
    val regimenCapital : String,
) : Persona(domicilioFiscal)

data class DomicilioFiscal(
    val cp : String,
    val estado: String,
    val municipio : String,
    val localidad : String,
    val colonia : String,
    val vialidad: String,
    val nombreVialidad : String,
    val numeroExterior : Int,
    val numeroInterior : Int,
    val entrecalle1 : String,
    val entrecalle2 : String,
    val referenciaAdicional : String,
    val caracteristicas : String,
    val actividadEconomica : String,
    val regimenFiscal : String
)

class StateListViewModel {
    val nValidoRegex = "[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+( (Del |De La|El)?[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+)*".toRegex()
    var states = mutableStateListOf(
        Estado(0, "Guanajuato", "GTO", mutableStateListOf(Municipio(0, "Uriangato", "URI", mutableStateListOf()), Municipio(1, "Moroleón", "MORO", mutableStateListOf()), Municipio(2, "Irapuato", "IRA",  mutableStateListOf()))),
        Estado(1, "Jalisco",  "JAL", mutableStateListOf(Municipio(1, "Guadalajara", "GUA", mutableStateListOf()), Municipio(1, "Zapopan", "ZAP",mutableStateListOf()), Municipio(2, "Tlapelalque", "TLAP", mutableStateListOf()))),
        Estado(2,"Hidalgo", "HGO", mutableStateListOf()),
        Estado(3,"Colima",  "COL", mutableStateListOf()),
        Estado(4,"San Luis Potosi", "SLP", mutableStateListOf()),
        Estado(5,"Chihuahua", "CHIH", mutableStateListOf()),
        Estado(6,"Durango",  "DGO",mutableStateListOf()),
        Estado(7,"Aguascalientes", "AGS", mutableStateListOf()),
        Estado(8,"Oaxaca",  "OAX", mutableStateListOf()),
        Estado(9,"Yucatán", "YUC", mutableStateListOf()),
        Estado(10,"Veracruz", "VER",mutableStateListOf()),
        Estado(11,"Tlaxcala", "TLAX", mutableStateListOf()),
        Estado(12,"Estado De Mexico", "MEX",mutableStateListOf()),
        Estado(13,"Nuevo Leon",  "NVL", mutableStateListOf()),
        Estado(14,"Guerrero",  "GRO", mutableStateListOf()),
        Estado(15,"Campeche",  "CAMP", mutableStateListOf()),
        Estado(16,"Coahuila", "COAH", mutableStateListOf()),
        Estado(17,"Morelos", "MOR", mutableStateListOf()),
        Estado(18,"Nayarit", "NAY", mutableStateListOf()),
        Estado(19,"Queretaro", "QRO", mutableStateListOf()),
        Estado(20,"Quintana Roo",  "QRO", mutableStateListOf()),
        Estado(21,"Sonora",  "SON", mutableStateListOf()),
        Estado(22,"Tabasco", "TAB", mutableStateListOf()),
        Estado(23,"Tamaulipas", "TAMPS", mutableStateListOf()),
        Estado(24,"Zacatecas", "ZAC", mutableStateListOf()),
        Estado(25,"Ciudad de Mexico", "CDMX", mutableStateListOf()),
        Estado(26,"Baja California", "BC", mutableStateListOf()),
        Estado(27,"Baja California Sur", "BCS", mutableStateListOf()),
        Estado(28,"Chiapas",  "CHP", mutableStateListOf()),
        Estado(29,"Puebla",  "PUE", mutableStateListOf()),
        Estado(30,"Sinaloa", "SIN", mutableStateListOf()),
        Estado(31, "Míchoacán", "MIC", mutableStateListOf()),
        )
    init {
        states = states.sortedBy { it.nombre }.toMutableStateList()
    }

    fun RemoveEstado(nombre : String)
    {
        states.removeIf { it.nombre.lowercase().equals(nombre.lowercase()) }
    }
    fun AddEstado(name : String) : AnsStates
    {
        val n = name.trimEnd()
        val item = states.find { it.nombre.lowercase().equals(n.lowercase()) }
        if(item == null) {
            if (nValidoRegex.matches(n)) {
                val el = n.split(" ")
                var abr = ""
                val id = states.size;
                for (e in el) abr += e[0];
                if (abr.length == 1) abr += el[0][1];
                states.add(Estado(id, n, abr, mutableStateListOf()))
                states = states.sortedBy { it.nombre }.toMutableStateList()
                return AnsStates.Acepted
            }
            return AnsStates.BadFormat
        }
        return AnsStates.Repeted
    }
    fun UpdateEstado(original : String, name : String) : AnsStates
    {
        val n = name.trimEnd()
        val item = states.find { it.nombre.lowercase().equals(n.lowercase()) }
        if(item == null) {
            if (nValidoRegex.matches(n)) {
                states.removeIf { it.nombre.lowercase().equals(original.lowercase()) }
                val el = n.split(" ")
                var abr = ""
                val id = states.size;
                for (e in el) abr += e[0];
                if (abr.length == 1) abr += el[0][1];
                states.add(Estado(id, n, abr, mutableStateListOf()))
                states = states.sortedBy { it.nombre }.toMutableStateList()
                return AnsStates.Acepted
            }
            return AnsStates.BadFormat
        }
        return AnsStates.Repeted
    }
    fun AddMun(estado : String, name : String) : AnsStates
    {
        val n = name.trimEnd()
        val estado = states.find { it.nombre.lowercase().equals(estado.lowercase()) }
        val mun = estado?.municipios?.find { it.nombre.lowercase().equals(n.lowercase()) }
        if(mun == null) {
            if (nValidoRegex.matches(n)) {
                val el = n.split(" ")
                var abr = ""
                val id = estado?.municipios?.size ?: 0;
                for (e in el) abr += e[0];
                if (abr.length == 1) abr += el[0][1];
                estado?.municipios?.add(Municipio(id, n, abr, mutableStateListOf()))
                estado?.municipios = estado.municipios.sortedBy { it.nombre }.toMutableList()
                return AnsStates.Acepted
            }
            return AnsStates.BadFormat
        }
        return AnsStates.Repeted
    }
    fun DeleteMun(estado : String, mun : String)
    {
        val estado = states.find { it.nombre.lowercase().equals(estado.lowercase()) }
        estado?.municipios?.removeIf { it.nombre.lowercase().equals(mun.lowercase()) }
    }
    fun UpdateMun(estado : String,  original : String, mun : String) : AnsStates
    {
        val n = mun.trimEnd()
        val estado = states.find { it.nombre.lowercase().equals(estado.lowercase()) }
        val mun = estado?.municipios?.find { it.nombre.lowercase().equals(n.lowercase()) }
        if(mun == null) {
            if (nValidoRegex.matches(n)) {
                estado?.municipios?.removeIf { it.nombre.lowercase().equals(original.lowercase()) }
                val el = n.split(" ")
                var abr = ""
                val id = estado?.municipios?.size ?: 0;
                for (e in el) abr += e[0];
                if (abr.length == 1) abr += el[0][1];
                estado?.municipios?.add(Municipio(id, n, abr, mutableStateListOf()))
                estado?.municipios = estado.municipios.sortedBy { it.nombre }.toMutableList()
                return AnsStates.Acepted
            }
            return AnsStates.BadFormat
        }
        return AnsStates.Repeted
    }
    fun DeletePersona(est : String, mun : String, tipo : String, identify : String)
    {
        val estado = states.find { it.nombre.lowercase().equals(est.lowercase()) }
        if(estado == null) return
        val municipio = estado.municipios.find { it.nombre.lowercase().equals(mun.lowercase()) }
        if(municipio == null) return
        if(tipo.equals("fisica"))
        {
            municipio.contribuidores.removeIf { it is PersonaFisica && it.curp.equals(identify) }
            return
        }
        if(tipo.equals("moral"))
        {
            municipio.contribuidores.removeIf { it is PersonaMoral && it.rfcRepresentante.equals(identify) }
        }
    }
    fun AddPersona(est : String, mun : String, persona : Persona) : Boolean
    {
        val estado = states.find { it.nombre.lowercase().equals(est.lowercase()) }
        if(estado == null) return false
        val municipio = estado.municipios.find { it.nombre.lowercase().equals(mun.lowercase()) }
        if(municipio == null) return false
        if(municipio.contribuidores.find {
            it is PersonaFisica && persona is PersonaFisica && it.curp.equals((persona as PersonaFisica).curp)  ||
                it is PersonaMoral && persona is PersonaMoral &&
                    it.rfcRepresentante.equals((persona as PersonaMoral).rfcRepresentante)
        } == null) {
            municipio.contribuidores.add(persona)
            return true
        }
        return false
    }
}