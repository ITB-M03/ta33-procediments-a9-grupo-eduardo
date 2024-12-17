import java.util.*
import kotlin.math.round

fun main() {
   val scan= iniciarScan()
    iniciarMaquinaDeBitllets(scan)
}
    fun iniciarScan (): Scanner {
        var scan : Scanner = Scanner (System.`in`).useLocale(Locale.UK)
        return scan
    }

fun iniciarMaquinaDeBitllets(scan:Scanner) {
    val tipusBitllet = seleccionarTipusBitllet(scan)
    val zones = seleccionarZones(scan)
    val quantitat = seleccionarQuantitat(scan)
    val preuFinal = calcularPreuFinal(tipusBitllet, zones, quantitat)
    val canvi = processarPagament(preuFinal)
    scan.nextLine()
    println("Vols tiquet? (si/no)")
    var opcio=scan.nextLine()
    if (opcio=="si") mostrarTiquet(tipusBitllet, zones, quantitat, preuFinal, canvi)
}

fun seleccionarTipusBitllet(scan: Scanner): String {
    val opcions = listOf("Bitllet senzill", "TCasual", "TUsual", "TFamiliar", "TJove")
    var eleccio: String? = null
    for (index in opcions.indices) println("${index + 1}. ${opcions[index]}")
    while (eleccio == null) {
        val entrada = scan.next().toIntOrNull()
        if (entrada != null && entrada in 1..opcions.size) eleccio = opcions[entrada - 1]
    }
    return eleccio
}

fun seleccionarZones(scan:Scanner): Int {
    val opcions = listOf(1, 2, 3)
    var eleccio: Int? = null
    println("Selecciona el número de zones (1, 2 o 3):")
    while (eleccio == null) {
        val entrada = scan.next().toIntOrNull()
        if (entrada != null && entrada in opcions) eleccio = entrada
    }
    return eleccio
}

fun seleccionarQuantitat(scan: Scanner): Int {
    var eleccio: Int? = null
    println("Quants bitllets vols (màxim 3)?")
    while (eleccio == null) {
        val entrada = scan.next().toIntOrNull()
        if (entrada != null && entrada in 1..3) eleccio = entrada
    }
    return eleccio
}

fun calcularPreuFinal(tipus: String, zones: Int, quantitat: Int): Double {
    val preusBase = mapOf(
        "Bitllet senzill" to 2.40, "TCasual" to 11.35, "TUsual" to 40.00, "TFamiliar" to 10.00, "TJove" to 80.00
    )
    val multiplicadors = listOf(1.0, 1.3125, 1.8443)
    val preuBase = preusBase[tipus] ?: 0.0
    val multiplicador = multiplicadors[zones - 1]
    return round(preuBase * multiplicador * quantitat * 100) / 100
}

fun processarPagament(preuFinal: Double): Double {
    val monedes = listOf(0.05, 0.10, 0.20, 0.50, 1.00, 2.00, 5.00, 10.00, 20.00, 50.00)
    var totalIntroduit = 0.0
    println("Introdueix diners per un total de $preuFinal€:")
    while (totalIntroduit < preuFinal) {
        val entrada = readln().toDoubleOrNull()
        if (entrada != null && entrada in monedes) totalIntroduit += entrada
    }
    return round((totalIntroduit - preuFinal) * 100) / 100
}

fun mostrarTiquet(tipus: String, zones: Int, quantitat: Int, total: Double, canvi: Double) {
    println("--- Tiquet ---")
    println("Tipus de bitllet: $tipus")
    println("Zones: $zones")
    println("Quantitat: $quantitat")
    println("Total: $total€")
    println("Canvi: $canvi€")
    println("--- Gràcies! ---")
}
