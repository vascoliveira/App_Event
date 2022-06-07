package pt.ipg.appevent

import android.content.ContentValues

data class Organizador (
    var Nome_organizador : String,
    var idade: Int,
    var Telemovel: Int,
    var email: String,
    var id_localidade: Long,
    var id_evento: Long,
    var id: Long = -1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDOrganizador.nome_organizador, Nome_organizador)
        valores.put(TabelaBDOrganizador.Idade, idade)
        valores.put(TabelaBDOrganizador.telemovel, Telemovel)
        valores.put(TabelaBDOrganizador.Email, email)
        valores.put(TabelaBDOrganizador.Localidade_id, id_localidade)
        valores.put(TabelaBDOrganizador.evento_id, id_evento)

        return valores
    }
}