package pt.ipg.appevent

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class TipoEventos (
    var tipo_de_evento : String = "",
    var id: Long = -1
) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDTipoEvento.TIPO_EVENTO, tipo_de_evento)


        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): TipoEventos {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaBDTipoEvento.TIPO_EVENTO)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)

            return TipoEventos(nome, id)
        }
    }
}