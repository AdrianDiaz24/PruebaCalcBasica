package es.iesraprog2425.pruebaes.app

import es.iesraprog2425.pruebaes.data.RepoLogs
import es.iesraprog2425.pruebaes.model.Operadores
import es.iesraprog2425.pruebaes.redondear
import es.iesraprog2425.pruebaes.ui.IEntradaSalida

class Calculadora(private val ui: IEntradaSalida, private val repoLogs: RepoLogs = RepoLogs()) {

    private fun pedirNumero(msj: String, msjError: String = "Número no válido!"): Double {
        return ui.pedirDouble(msj) ?: throw InfoCalcException(msjError)
    }

    private fun pedirInfo(): Triple<Double, Operadores, Double> {
        var valorValido = false
        var num1 = 0.0
        var num2 = 0.0
        var operador = Operadores.SUMA
        while (!valorValido) {
            try {
                num1 = pedirNumero("Introduce el primer número: ", "El primer número no es válido!")
                valorValido = true
            } catch (e: InfoCalcException) {
                repoLogs.agregarLog("${e.message}")
                ui.mostrarError("${e.message}")
            }
        }
        valorValido = false
        while (!valorValido) {
            try {
                operador = Operadores.getOperador(ui.pedirInfo("Introduce el operador (+, -, *, /): ").firstOrNull())
                    ?: throw InfoCalcException("El operador no es válido!")
                valorValido = true
            } catch (e: InfoCalcException) {
                repoLogs.agregarLog("${e.message}")
                ui.mostrarError("${e.message}")
            }
        }
        valorValido = false
        while (!valorValido) {
            try {
                num2 = pedirNumero("Introduce el segundo número: ", "El segundo número no es válido!")
                valorValido = true
            } catch (e: InfoCalcException) {
                repoLogs.agregarLog("${e.message}")
                ui.mostrarError("${e.message}")
            }
        }

        return Triple(num1, operador, num2)
    }

    private fun realizarCalculo(numero1: Double, operador: Operadores, numero2: Double) =
        when (operador) {
            Operadores.SUMA -> numero1 + numero2
            Operadores.RESTA -> numero1 - numero2
            Operadores.MULTIPLICACION -> numero1 * numero2
            Operadores.DIVISION -> numero1 / numero2
        }

    fun iniciar() {
        do {
            try {
                repoLogs.comprobarRuta()
                ui.limpiarPantalla(4)
                val (numero1, operador, numero2) = pedirInfo()
                val resultado = realizarCalculo(numero1, operador, numero2)
                ui.mostrar("$numero1 ${operador.simbolos[0]} $numero2 = ${resultado.redondear(2)}")
                repoLogs.agregarLog("$numero1 ${operador.simbolos[0]} $numero2 = ${resultado.redondear(2)}")
            } catch (e: NumberFormatException) {
                ui.mostrarError(e.message ?: "Se ha producido un error!")
            }
        } while (ui.preguntar())
        repoLogs.subirLogs()
        ui.limpiarPantalla()
    }

}