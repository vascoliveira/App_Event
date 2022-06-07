package pt.ipg.appevent

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDLocalidade (db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {

        db.execSQL("CREATE TABLE $nome(${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $nome_localidade TEXT NOT NULL, $distrito TEXT NOT NULL, $Evento_id INTEGER NOT NULL, FOREIGN KEY ($Evento_id._ID) REFERENCES ${TabelaBDEvento.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT) $localidade_id INTEGER NOT NULL, FOREIGN KEY ($localidade_id._ID) REFERENCES ${TabelaBDLocalidade.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT)")

    }

    companion object {
        const val NOME = "Localidade"
        const val nome_localidade = "Nome_localidade"
        const val distrito = "Distrtio"
        const val localidade_id = "Localidade_id"
        const val Evento_id = "evento_id"


    }
}