package pt.ipg.appevent

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDOrganizador(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
            db.execSQL("CREATE TABLE $nome(${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $Idade TEXT NOT NULL, $telemovel TEXT NOT NULL, $email TEXT NOT NULL $evento_id INTEGER NOT NULL, FOREIGN KEY ($Evento._ID) REFERENCES ${TabelaBDEvento.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT) $Localidade_id INTEGER NOT NULL, FOREIGN KEY ($Localidade._ID) REFERENCES ${TabelaBDLocalidade.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT)")
        }

        companion object {
            const val NOME = "Organizador"
            const val Idade = "idade"
            const val telemovel = "telemovel"
            const val email = "email"
            const val Localidade_id = "Localidade_id"
            const val evento_id = "evento_id"


        }
    }