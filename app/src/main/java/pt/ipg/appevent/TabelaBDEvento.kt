package pt.ipg.appevent

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDEvento(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL(
            "CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $NOME_EVENTO TEXT NOT NULL," +
                    " $DATA TEXT NOT NULL, $DESCRICAO TEXT NOT NULL, $LOCALIDADE REFERENCES ${TabelaBDLocalidade.NOME_LOCALIDADE} ON DELETE RESTRICT, " +
                    "$TIPO_EVENTOS REFERENCES ${TabelaBDTipoEvento.TIPO_EVENTO} ON DELETE RESTRICT,"+
                    " $ORGANIZADOR_ID REFERENCES ${TabelaBDOrganizador.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT)")

    }
    companion object {
        const val NOME = "Eventos"
        const val NOME_EVENTO = "Nome_Evento"
        const val DATA = "Data"
        const val DESCRICAO = "descricao"
        const val LOCALIDADE = "Localidade"
        const val TIPO_EVENTOS = "Tipos Eventos"
        const val ORGANIZADOR_ID = "organizador"

    }
}

