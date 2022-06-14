package pt.ipg.appevent

import android.content.ContentValues

data class Localidade (
    var Nome_Localidade : String,
    var Distrito: String,
    var id_organizador: Int,
    var id_evento: Long,
    var id: Long = -1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDLocalidade.nome_localidade, Nome_Localidade)
        valores.put(TabelaBDLocalidade.distrito, Distrito)
        valores.put(TabelaBDLocalidade.organizador_id, id_organizador)
        valores.put(TabelaBDLocalidade.Evento_id, id_evento)

        return valores
    }
}