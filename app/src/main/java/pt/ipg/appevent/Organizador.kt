package pt.ipg.appevent

import android.content.ContentValues

data class Organizador (
    var Nome_organizador : String,
    var idade: String,
    var Telemovel: String,
    var email: String,
    var localidade: String,
    var id: Long = -1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDOrganizador.nome_organizador, Nome_organizador)
        valores.put(TabelaBDOrganizador.Idade, idade)
        valores.put(TabelaBDOrganizador.telemovel, Telemovel)
        valores.put(TabelaBDOrganizador.Email, email)


        return valores
    }
}