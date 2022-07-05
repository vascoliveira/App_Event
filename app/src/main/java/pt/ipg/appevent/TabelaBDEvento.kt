package pt.ipg.appevent

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

class TabelaBDEvento(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL(
            "CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $NOME_EVENTO TEXT NOT NULL," +
                    " $DATA TEXT NOT NULL, " +
                    "$DESCRICAO TEXT NOT NULL," +
                    " $ORGANIZADOR_ID TEXT NOT NULL ," +
                    " $LOCALIDADE_ID INTEGER NOT NULL ," +
                    "$TIPO_EVENTOS_ID INTEGER NOT NULL," +
                    " FOREIGN KEY($ORGANIZADOR_ID) REFERENCES ${TabelaBDOrganizador.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT,"+
                    "FOREIGN KEY ($LOCALIDADE_ID) REFERENCES ${TabelaBDLocalidade.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT, " +
                    "FOREIGN KEY($TIPO_EVENTOS_ID)REFERENCES ${TabelaBDTipoEvento.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }
    override fun query(
        columns: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor {
        val queryBuilder = SQLiteQueryBuilder()
        queryBuilder.tables = "$NOME INNER JOIN ${TabelaBDTipoEvento.NOME} ON ${TabelaBDTipoEvento.CAMPO_ID} = $TIPO_EVENTOS_ID"

        return queryBuilder.query(db, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

        companion object {
        const val NOME = "Eventos"

        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
        const val NOME_EVENTO = "Nome_Evento"
        const val DATA = "Data"
        const val DESCRICAO = "descricao"
        const val ORGANIZADOR_ID = "idorganizador"
        const val LOCALIDADE_ID = "Localidade"
        const val TIPO_EVENTOS_ID= "Tipos_Eventos"

        val TODAS_COLUNAS = arrayOf(CAMPO_ID, NOME_EVENTO, DATA, DESCRICAO, ORGANIZADOR_ID,
            LOCALIDADE_ID, TIPO_EVENTOS_ID,TabelaBDTipoEvento.TIPO_EVENTO)
    }
}

