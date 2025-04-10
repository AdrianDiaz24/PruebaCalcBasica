package es.iesraprog2425.pruebaes.data

interface IRepoLogs {

    val logs: MutableList<String>

    fun agregarLog(log: String): Boolean{
        return logs.add(log)
    }

    fun subirLogs(ruta: String = "./log")

    fun mostrarUltimoLog()

    fun comprobarRuta(ruta: String = "./log")
}