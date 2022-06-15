package pt.ipg.appevent

import android.content.ContentValues

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
}