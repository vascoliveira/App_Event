package pt.ipg.appevent

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDLocalidade (db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {

        db.execSQL("CREATE TABLE $nome(${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $nome_localidade REFERENCES ${TabelaBDEvento.Localidade} ON DELETE RESTRICT)")
    }

    companion object {
        const val NOME = "Localidade"
        const val nome_localidade = "Nome_localidade"

    }
}