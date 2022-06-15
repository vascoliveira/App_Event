package pt.ipg.appevent

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDTipoEvento (db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {

        db.execSQL("CREATE TABLE $nome(${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $Tipo_Evento REFERENCES ${TabelaBDEvento.Tipo_eventos} ON DELETE RESTRICT)")
    }

    companion object {
        const val NOME = "Localidade"
        const val Tipo_Evento = "Tipo de Evento"

    }
}