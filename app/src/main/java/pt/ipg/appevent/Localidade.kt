package pt.ipg.appevent

import android.content.ContentValues

data class Localidade (
    var Nome_Localidade : String,
    var id: Long = -1
) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDLocalidade.NOME_LOCALIDADE, Nome_Localidade)


        return valores
    }
}
