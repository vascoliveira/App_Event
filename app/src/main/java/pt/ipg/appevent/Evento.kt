package pt.ipg.appevent

import android.content.ContentValues

data class Evento (
    var Nome_Evento : String,
    var Data: String,
    var Descricao: String,
    var localidade: String,
    var tipo_eventos:String,
    var id_Organizador: Long,
    var id: Long = -1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDEvento.NOME_EVENTO, Nome_Evento)
        valores.put(TabelaBDEvento.DATA, Data)
        valores.put(TabelaBDEvento.DESCRICAO, Descricao)
        valores.put(TabelaBDEvento.LOCALIDADE, localidade)
        valores.put(TabelaBDEvento.TIPO_EVENTOS, tipo_eventos)
        valores.put(TabelaBDEvento.ORGANIZADOR_ID, id_Organizador)

        return valores
    }
}