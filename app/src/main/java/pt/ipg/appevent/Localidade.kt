package pt.ipg.appevent

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Localidade (
    var Nome_Localidade : String,
    var id: Long = -1
) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDLocalidade.NOME_LOCALIDADE, Nome_Localidade)


        return valores
    }
    companion object {
        fun fromCursor(cursor: Cursor) : Localidade {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaBDLocalidade.NOME_LOCALIDADE)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)

            return Localidade(nome, id)
        }
}
}

