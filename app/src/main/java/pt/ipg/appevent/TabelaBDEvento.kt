package pt.ipg.appevent

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDEvento(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
        db.execSQL(
            "CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $nome_evento TEXT NOT NULL," +
                    " $data TEXT NOT NULL, $descricao TEXT NOT NULL, $Localidade TEXT NOT NULL, $Tipo_eventos TEXT NOT NULL," +
                    " $organizador_id INTEGER NOT NULL, FOREIGN KEY (${organizador_id}) REFERENCES ${TabelaBDOrganizador.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT)"
        )

    }
    companion object {
        const val NOME = "Eventos"
        const val nome_evento = "Nome_Evento"
        const val data = "Data"
        const val descricao = "descricao"
        const val Localidade = "Localidade"
        const val Tipo_eventos = "Tipos Eventos"
        const val organizador_id = "organizador"

    }
}

