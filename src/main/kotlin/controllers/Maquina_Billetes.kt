import java.util.*
import kotlin.math.round
/**
 * @author Ivan Torres & Denis Coello
 * Funcio principal
 */
fun main() {
    val scan = iniciarScan()
    iniciarMaquinaDeBitllets(scan)
}
/**
 * @author Ivan Torres & Denis Coello
 * Funcio per iniciar escaner
 */
fun iniciarScan(): Scanner {
    return Scanner(System.`in`).useLocale(Locale.UK)
}


/**
 * @author Ivan Torres & Denis Coello
 * @param scan
 * Funcio que inicia el procés de la maquina de bitllets
 */
fun iniciarMaquinaDeBitllets(scan: Scanner) {
    var continuar = true
    while (continuar) {
        if (comprovarContrasenya(scan)) {
            println("Programa aturat per l'operador.")
            continuar = false
        } else {
            val tipusBitllet = seleccionarTipusBitllet(scan)
            val zones = seleccionarZones(scan)
            val quantitat = seleccionarQuantitat(scan)
            val preuFinal = calcularPreuFinal(tipusBitllet, zones, quantitat)
            val canvi = processarPagament(scan, preuFinal)
            scan.nextLine()
            if (volTiquet(scan)) mostrarTiquet(tipusBitllet, zones, quantitat, preuFinal, canvi)
        }
    }
}
/**
 * @author Ivan Torres & Denis Coello
 * @param scan
 * Funcio per comprovar la contrasenya per si s'ha d'aturar
 */
fun comprovarContrasenya(scan: Scanner): Boolean {
    println("Introdueix la contrasenya per aturar el programa o prem Enter per continuar:")
    val entrada = scan.nextLine()
    return entrada == "4321"
}
/**
 * @author Ivan Torres & Denis Coello
 * @param scan
 * Funcio per seleccionar quin tipus de bitllet es vol comprar
 */
fun seleccionarTipusBitllet(scan: Scanner): String {
    val opcions = listOf("Bitllet senzill", "TCasual", "TUsual", "TFamiliar", "TJove")
    var eleccio: String? = null
    while (eleccio == null) {
        println("Selecciona el tipus de bitllet o escriu 'enrere' per tornar al menú principal:")
        for (index in opcions.indices) println("${index + 1}. ${opcions[index]}")
        val entrada = scan.nextLine()
        eleccio = if (entrada.lowercase() == "enrere") seleccionarTipusBitllet(scan)
        else opcions.getOrNull(entrada.toIntOrNull()?.minus(1) ?: -1)
    }
    return eleccio
}
/**
 * @author Ivan Torres & Denis Coello
 * @param scan
 * Funcio per seleccionar les zones del bitllet
 */
fun seleccionarZones(scan: Scanner): Int {
    val opcions = listOf(1, 2, 3)
    var eleccio: Int? = null
    while (eleccio == null) {
        println("Selecciona el número de zones (1, 2 o 3) o escriu 'enrere' per tornar a l'opció anterior:")
        val entrada = scan.nextLine()
        eleccio = if (entrada.lowercase() == "enrere") seleccionarZones(scan)
        else entrada.toIntOrNull()?.takeIf { it in opcions }
    }
    return eleccio
}
/**
 * @author Ivan Torres & Denis Coello
 * @param scan
 * Funcio per seleccionar la cuantitat de bitllets
 */
fun seleccionarQuantitat(scan: Scanner): Int {
    var eleccio: Int? = null
    while (eleccio == null) {
        println("Quants bitllets vols (màxim 3) o escriu 'enrere' per tornar a l'opció anterior:")
        val entrada = scan.nextLine()
        eleccio = if (entrada.lowercase() == "enrere") seleccionarQuantitat(scan)
        else entrada.toIntOrNull()?.takeIf { it in 1..3 }
    }
    return eleccio
}
/**
 * @author Ivan Torres & Denis Coello
 * @param tipus
 * @param zones
 * @param quantitat
 * @return preu arrodonit
 * Funcio que calcula el preu final segins el tipus, la zona i la quantitat
 */
fun calcularPreuFinal(tipus: String, zones: Int, quantitat: Int): Double {
    val preusBase = mapOf(
        "Bitllet senzill" to 2.40, "TCasual" to 11.35, "TUsual" to 40.00, "TFamiliar" to 10.00, "TJove" to 80.00
    )
    val multiplicadors = listOf(1.0, 1.3125, 1.8443)
    val preuBase = preusBase[tipus] ?: 0.0
    val multiplicador = multiplicadors[zones - 1]
    return round(preuBase * multiplicador * quantitat * 100) / 100
}
/**
 * @author Ivan Torres & Denis Coello
 * @param scan
 * @param preuFinal
 * Funcio que processa el pagament
 */
fun processarPagament(scan: Scanner, preuFinal: Double): Double {
    val monedes = listOf(0.05, 0.10, 0.20, 0.50, 1.00, 2.00, 5.00, 10.00, 20.00, 50.00)
    var totalIntroduit = 0.0
    while (totalIntroduit < preuFinal) {
        val restant = round((preuFinal - totalIntroduit) * 100) / 100
        println("Falten $restant€ per pagar. Introdueix diners:")
        val entrada = scan.nextLine().toDoubleOrNull()
        totalIntroduit += if (entrada != null && entrada in monedes) entrada else 0.0
    }
    return round((totalIntroduit - preuFinal) * 100) / 100
}
/**
 * @author Ivan Torres & Denis Coello
 * @param scan
 * @return resposta
 * Funcio que demana si vols tiquet o no
 */
fun volTiquet(scan: Scanner): Boolean {
    println("Vols tiquet? (si/no)")
    return scan.nextLine().lowercase() == "si"
}
/**
 * @author Ivan Torres & Denis Coello
 * @param tipus
 * @param zones
 * @param quantitat
 * @param total
 * @param canvi
 * Funció que imprimeix el ticket
 */
fun mostrarTiquet(tipus: String, zones: Int, quantitat: Int, total: Double, canvi: Double) {
    println("--- Tiquet ---")
    println("Tipus de bitllet: $tipus")
    println("Zones: $zones")
    println("Quantitat: $quantitat")
    println("Total: $total€")
    println("Canvi: $canvi€")
    println("--- Gràcies! ---")
}

