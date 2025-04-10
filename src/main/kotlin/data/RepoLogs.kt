package es.iesraprog2425.pruebaes.data

import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RepoLogs: IRepoLogs {
    override val logs: MutableList<String> = mutableListOf()

    override fun subirLogs(ruta: String) {
        val formatoFecha = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        val archivo = File("$ruta/log${LocalDateTime.now().format(formatoFecha)}.txt")
        for (log in logs) {
            archivo.appendText("$log\n")
        }
    }

    override fun mostrarUltimoLog() {
        TODO("Not yet implemented")
    }

    override fun comprobarRuta(ruta: String) {
        val directorio = File(ruta)
        if (!directorio.exists()){
            directorio.mkdirs()
            println("Ruta $ruta creada")
        }
    }
}