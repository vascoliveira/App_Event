package pt.ipg.appevent

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Organizador (
    var Nome_organizador : String,
    var idade: Int,
    var Telemovel: String,
    var email: String,
    var id: Long = -1
) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues()
        valores.put(TabelaBDOrganizador.NOME_ORGANIZADOR, Nome_organizador)
        valores.put(TabelaBDOrganizador.IDADE, idade)
        valores.put(TabelaBDOrganizador.TELEMOVEL, Telemovel)
        valores.put(TabelaBDOrganizador.EMAIL, email)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Organizador {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaBDOrganizador.NOME)
            val posEmail = cursor.getColumnIndex(TabelaBDOrganizador.EMAIL)
            val posIdade = cursor.getColumnIndex(TabelaBDOrganizador.IDADE)
            val posTelemovel = cursor.getColumnIndex(TabelaBDOrganizador.TELEMOVEL)




            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)
            val email = cursor.getString(posEmail)
            val idade = cursor.getInt(posIdade)
            val telemovel = cursor.getString(posTelemovel)

            return Organizador(nome,idade,telemovel,email,id)
        }
    }
}