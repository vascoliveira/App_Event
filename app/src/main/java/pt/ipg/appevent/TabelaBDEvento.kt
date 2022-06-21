package pt.ipg.appevent

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDEvento(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL(
            "CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $NOME_EVENTO TEXT NOT NULL," +
                    " $DATA TEXT NOT NULL, " +
                    "$DESCRICAO TEXT NOT NULL," +
                    " $ORGANIZADOR_ID INTEGER NOT NULL ," +
                    " $LOCALIDADE_ID INTEGER NOT NULL ," +
                    "$TIPO_EVENTOS_ID INTEGER NOT NULL," +
                    " FOREIGN KEY($ORGANIZADOR_ID) REFERENCES ${TabelaBDOrganizador.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT,"+
                    "FOREIGN KEY ($LOCALIDADE_ID) REFERENCES ${TabelaBDLocalidade.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT, " +
                    "FOREIGN KEY($TIPO_EVENTOS_ID)REFERENCES ${TabelaBDTipoEvento.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT)")


    }
    companion object {
        const val NOME = "Eventos"
        const val NOME_EVENTO = "Nome_Evento"
        const val DATA = "Data"
        const val DESCRICAO = "descricao"
        const val ORGANIZADOR_ID = "idorganizador"
        const val LOCALIDADE_ID = "Localidade"
        const val TIPO_EVENTOS_ID= "Tipos_Eventos"


    }
}

