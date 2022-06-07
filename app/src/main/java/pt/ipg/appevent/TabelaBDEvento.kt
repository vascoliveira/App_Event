package pt.ipg.appevent

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDEventos(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL("CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $nome_evento TEXT NOT NULL, $data TEXT NOT NULL, $descricao TEXT NOT NULL $id_localidade INTEGER NOT NULL, FOREIGN KEY ($localidade._ID) REFERENCES ${TabelaBDLocalidade.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    companion object {
        const val NOME = "Eventos"
        const val nome_evento = "Nome_Evento"
        const val data = "Data"
        const val descricao = "descricao"
        const val id_localidade = "localidade_id"
        const val organizador_id = "organizador_id"
        adas

    }
}