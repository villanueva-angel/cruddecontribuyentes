package viewModel

import androidx.compose.runtime.mutableStateListOf
import com.pantherhm.cruddecontribuyentes.data.ContribuyentesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    val numeroExterior : String,
    val numeroInterior : String,
    val entrecalle1 : String,
    val entrecalle2 : String,
    val referenciaAdicional : String,
    val caracteristicas : String,
    val actividadEconomica : String,
    val regimenFiscal : String
)

class StateListViewModel(private val repository: ContribuyentesRepository) {
    val nValidoRegex = "[A-ZÃÃ‰ÃÃ“ÃšÃ‘][a-zÃ¡Ã©Ã­Ã³ÃºÃ±]+( (Del |De La|El)?[A-ZÃÃ‰ÃÃ“ÃšÃ‘][a-zÃ¡Ã©Ã­Ã³ÃºÃ±]+)*".toRegex()

    private val defaultStates = listOf(
        Estado(0, "Guanajuato", "GTO", mutableStateListOf()),
        Estado(1, "Jalisco",  "JAL", mutableStateListOf()),
        Estado(2,"Hidalgo", "HGO", mutableStateListOf()),
        Estado(3,"Colima",  "COL", mutableStateListOf()),
        Estado(4,"San Luis Potosi", "SLP", mutableStateListOf()),
        Estado(5,"Chihuahua", "CHIH", mutableStateListOf()),
        Estado(6,"Durango",  "DGO",mutableStateListOf()),
        Estado(7,"Aguascalientes", "AGS", mutableStateListOf()),
        Estado(8,"Oaxaca",  "OAX", mutableStateListOf()),
        Estado(9,"YucatÃ¡n", "YUC", mutableStateListOf()),
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
        Estado(31, "MÃ­choacÃ¡n", "MIC", mutableStateListOf()),
    )

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private var syncJob: Job? = null
    private var syncVersion = 0L

    var states = mutableStateListOf<Estado>()

    init {
        syncStatesFromDatabase()
    }

    fun RemoveEstado(nombre : String) {
        val estado = states.find { it.nombre.equals(nombre, ignoreCase = true) } ?: return
        repository.deleteEstado(estado.id)
        states.remove(estado)
        syncStatesFromDatabase()
    }

    fun AddEstado(name : String) : AnsStates {
        val n = name.trimEnd()
        val item = states.find { it.nombre.equals(n, ignoreCase = true) }
        if(item == null) {
            if (nValidoRegex.matches(n)) {
                repository.insertEstado(n, createAbbreviation(n))
                syncStatesFromDatabase()
                return AnsStates.Acepted
            }
            return AnsStates.BadFormat
        }
        return AnsStates.Repeted
    }

    fun UpdateEstado(original : String, name : String) : AnsStates {
        val n = name.trimEnd()
        val current = states.find { it.nombre.equals(original, ignoreCase = true) } ?: return AnsStates.BadFormat
        val item = states.find { it.nombre.equals(n, ignoreCase = true) && it.id != current.id }
        if(item == null) {
            if (nValidoRegex.matches(n)) {
                repository.updateEstado(current.id, n, createAbbreviation(n))
                syncStatesFromDatabase()
                return AnsStates.Acepted
            }
            return AnsStates.BadFormat
        }
        return AnsStates.Repeted
    }

    fun AddMun(estado : String, name : String) : AnsStates {
        val n = name.trimEnd()
        val estadoActual = states.find { it.nombre.equals(estado, ignoreCase = true) }
        val mun = estadoActual?.municipios?.find { it.nombre.equals(n, ignoreCase = true) }
        if(mun == null) {
            if (nValidoRegex.matches(n)) {
                if (estadoActual == null) return AnsStates.BadFormat
                repository.insertMunicipio(estadoActual.id, n, createAbbreviation(n))
                syncStatesFromDatabase()
                return AnsStates.Acepted
            }
            return AnsStates.BadFormat
        }
        return AnsStates.Repeted
    }

    fun DeleteMun(estado : String, mun : String) {
        val estadoActual = states.find { it.nombre.equals(estado, ignoreCase = true) } ?: return
        val municipio = estadoActual.municipios.find { it.nombre.equals(mun, ignoreCase = true) } ?: return
        repository.deleteMunicipio(municipio.id)
        estadoActual.municipios.remove(municipio)
        syncStatesFromDatabase()
    }

    fun UpdateMun(estado : String,  original : String, mun : String) : AnsStates {
        val n = mun.trimEnd()
        val estadoActual = states.find { it.nombre.equals(estado, ignoreCase = true) } ?: return AnsStates.BadFormat
        val current = estadoActual.municipios.find { it.nombre.equals(original, ignoreCase = true) } ?: return AnsStates.BadFormat
        val municipio = estadoActual.municipios.find { it.nombre.equals(n, ignoreCase = true) && it.id != current.id }
        if(municipio == null) {
            if (nValidoRegex.matches(n)) {
                repository.updateMunicipio(current.id, n, createAbbreviation(n))
                syncStatesFromDatabase()
                return AnsStates.Acepted
            }
            return AnsStates.BadFormat
        }
        return AnsStates.Repeted
    }

    fun DeletePersona(est : String, mun : String, tipo : String, identify : String) {
        val estado = states.find { it.nombre.equals(est, ignoreCase = true) } ?: return
        val municipio = estado.municipios.find { it.nombre.equals(mun, ignoreCase = true) } ?: return
        repository.deletePersona(municipio.id, tipo, identify)
        municipio.contribuidores.removeIf{
            it is PersonaFisica && it.curp == identify ||
                    it is PersonaMoral && it.rfcRepresentante == identify
        }
        syncStatesFromDatabase()
    }

    fun AddPersona(est : String, mun : String, persona : Persona) : Boolean {
        val estado = states.find { it.nombre.equals(est, ignoreCase = true) } ?: return false
        val municipio = estado.municipios.find { it.nombre.equals(mun, ignoreCase = true) } ?: return false
        val alreadyExists = municipio.contribuidores.any {
            it is PersonaFisica && persona is PersonaFisica && it.curp.equals(persona.curp, ignoreCase = true) ||
                it is PersonaMoral && persona is PersonaMoral && it.rfcRepresentante.equals(persona.rfcRepresentante, ignoreCase = true)
        }
        if (!alreadyExists) {
            repository.insertPersona(municipio.id, persona)
            syncStatesFromDatabase()
            return true
        }
        return false
    }

    fun UpdatePersona(est : String, mun : String, tipo : String, original: String, persona: Persona) : Boolean {
        val estado = states.find { it.nombre.equals(est, ignoreCase = true) } ?: return false
        val municipio = estado.municipios.find { it.nombre.equals(mun, ignoreCase = true) } ?: return false
        val duplicated = municipio.contribuidores.any {
            it is PersonaFisica && persona is PersonaFisica && !persona.curp.equals(original, ignoreCase = true) &&
                it.curp.equals(persona.curp, ignoreCase = true) ||
                it is PersonaMoral && persona is PersonaMoral && !persona.rfcRepresentante.equals(original, ignoreCase = true) &&
                it.rfcRepresentante.equals(persona.rfcRepresentante, ignoreCase = true)
        }
        if (!duplicated) {
            repository.updatePersona(municipio.id, tipo, original, persona)
            syncStatesFromDatabase()
            return true
        }
        return false
    }

    private fun syncStatesFromDatabase() {
        val requestedVersion = ++syncVersion
        syncJob?.cancel()
        syncJob = viewModelScope.launch {
            val refreshedStates = withContext(Dispatchers.Default) {
                val storedStates = repository.getAllStates()
                if (storedStates.isEmpty()) {
                    repository.seedStates(defaultStates)
                }
                repository.getAllStates()
                    .sortedBy { it.nombre }
            }

            if (requestedVersion != syncVersion) return@launch

            states.clear()
            states.addAll(refreshedStates)
        }
    }

    private fun createAbbreviation(value: String): String {
        val words = value.split(" ").filter { it.isNotBlank() }
        var abbreviation = words.joinToString(separator = "") { it.first().toString() }
        if (abbreviation.length == 1 && words.first().length > 1) {
            abbreviation += words.first()[1]
        }
        return abbreviation.uppercase()
    }
}
