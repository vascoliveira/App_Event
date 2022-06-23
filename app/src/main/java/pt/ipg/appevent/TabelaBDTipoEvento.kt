package pt.ipg.appevent

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDTipoEvento (db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {

        db.execSQL("CREATE TABLE $nome(${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $TIPO_EVENTO TEXT NOT NULL)")
    }    companion object {
        const val NOME = "TIPOEVENTOS"

        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
        const val TIPO_EVENTO = "TipodeEvento"

        val TODAS_COLUNAS = arrayOf(CAMPO_ID, TIPO_EVENTO)

    }
}