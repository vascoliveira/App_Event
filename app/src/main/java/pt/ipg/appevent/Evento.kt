package pt.ipg.appevent

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Evento (
    var Nome_Evento : String,
    var Data: String,
    var Descricao: String,
    var organizador: Organizador,
    var localidade: Localidade,
    var tipo_eventos: TipoEventos,
    var id: Long = -1
) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues()

        valores.put(TabelaBDEvento.NOME_EVENTO, Nome_Evento)
        valores.put(TabelaBDEvento.DATA, Data)
        valores.put(TabelaBDEvento.DESCRICAO, Descricao)
        valores.put(TabelaBDEvento.ORGANIZADOR_ID, organizador.id)
        valores.put(TabelaBDEvento.LOCALIDADE_ID, localidade.id)
        valores.put(TabelaBDEvento.TIPO_EVENTOS_ID, tipo_eventos.id)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Evento {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNom = cursor.getColumnIndex(TabelaBDEvento.NOME_EVENTO)
            val posData = cursor.getColumnIndex(TabelaBDEvento.DATA)
            val posDesc = cursor.getColumnIndex(TabelaBDEvento.DESCRICAO)
            val posIdOrganizador = cursor.getColumnIndex(TabelaBDEvento.ORGANIZADOR_ID)
            val posIdLocalidade = cursor.getColumnIndex(TabelaBDEvento.LOCALIDADE_ID)
            val posIdTipoEventos = cursor.getColumnIndex(TabelaBDEvento.TIPO_EVENTOS_ID)
            val posNomeOrganizador = cursor.getColumnIndex(TabelaBDOrganizador.NOME_ORGANIZADOR)
            val posIdadeOrganizador = cursor.getColumnIndex(TabelaBDOrganizador.IDADE)
            val posEmailOrganizador = cursor.getColumnIndex(TabelaBDOrganizador.EMAIL)
            val posTelemovelOrganizador = cursor.getColumnIndex(TabelaBDOrganizador.TELEMOVEL)
            val posNomeLocalidade = cursor.getColumnIndex(TabelaBDLocalidade.NOME_LOCALIDADE)
            val posNomeTipoEvento = cursor.getColumnIndex(TabelaBDTipoEvento.TIPO_EVENTO)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNom)
            val data = cursor.getString(posData)
            val descricao = cursor.getString(posDesc)
            val idOrganizador = cursor.getLong(posIdOrganizador)
            val idLocalidade = cursor.getLong(posIdLocalidade)
            val idTipoEvento = cursor.getLong(posIdTipoEventos)

            val nomeOrganizador = cursor.getString(posNomeOrganizador)
            val idadeOrganizador = cursor.getInt(posIdadeOrganizador)
            val emailOrganizador = cursor.getString(posEmailOrganizador)
            val telemovelOrganizador = cursor.getString(posTelemovelOrganizador)

            val nomeLocalidade = cursor.getString(posNomeLocalidade)
            val nomeTipoEvento = cursor.getString(posNomeTipoEvento)


            val organizador = Organizador(
                nomeOrganizador,
                idadeOrganizador,
                telemovelOrganizador,
                emailOrganizador,
                idOrganizador
            )
            val localidade = Localidade(nomeLocalidade, idLocalidade)
            val tipo_evento = TipoEventos(nomeTipoEvento, idTipoEvento)

            return Evento (nome, data, descricao, organizador, localidade, tipo_evento, id)
        }
    }
}