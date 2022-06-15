package pt.ipg.appevent

import android.content.ContentValues

data class Evento (
    var Nome_Evento : String,
    var Data: String,
    var Descricao: Long,
    var localidade: String,
    var tipo_eventos:String,
    var id_Organizador: Long,
    var id: Long = -1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDEvento.nome_evento, Nome_Evento)
        valores.put(TabelaBDEvento.data, Data)
        valores.put(TabelaBDEvento.descricao, Descricao)
        valores.put(TabelaBDEvento.Localidade, localidade)
        valores.put(TabelaBDEvento.Tipo_eventos, tipo_eventos)
        valores.put(TabelaBDEvento.organizador_id, id_Organizador)

        return valores
    }
}