package pt.ipg.appevent

import android.content.ContentValues

data class Evento (
    var Nome_Evento : String,
    var Data: String,
    var Descricao: String,
    var idorganizador: Long,
    var idlocalidade: Long,
    var id_tipo_eventos: Long,
    var id: Long = -1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDEvento.NOME_EVENTO, Nome_Evento)
        valores.put(TabelaBDEvento.DATA, Data)
        valores.put(TabelaBDEvento.DESCRICAO, Descricao)
        valores.put(TabelaBDEvento.ORGANIZADOR_ID, idorganizador)
        valores.put(TabelaBDEvento.LOCALIDADE_ID, idlocalidade)
        valores.put(TabelaBDEvento.TIPO_EVENTOS_ID, id_tipo_eventos)


        return valores
    }
}