package pt.ipg.appevent

import android.content.ContentValues

data class Localidade (
    var Nome_Localidade : String,
    var Distrito: String,
    var id_localidade: Long,
    var id_evento: Long,
    var id: Long = -1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDLocalidade.nome_localidade, Nome_Localidade)
        valores.put(TabelaBDLocalidade.distrito, Distrito)
        valores.put(TabelaBDLocalidade.localidade_id, id_localidade)
        valores.put(TabelaBDLocalidade.Evento_id, id_evento)

        return valores
    }
}