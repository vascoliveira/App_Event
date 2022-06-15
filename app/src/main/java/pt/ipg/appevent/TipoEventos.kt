package pt.ipg.appevent

import android.content.ContentValues

data class TipoEventos (
    var tipo_de_evento : String,
    var id: Long = -1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDTipoEvento.TIPO_EVENTO, tipo_de_evento)


        return valores
    }
}