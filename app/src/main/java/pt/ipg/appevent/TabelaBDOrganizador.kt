package pt.ipg.appevent

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDOrganizador(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
            db.execSQL("CREATE TABLE $nome(${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,$nome_organizador TEXT NOT NULL," +
                    " $Idade TEXT NOT NULL, $telemovel TEXT NOT NULL, $Email TEXT NOT NULL, $evento_id INTEGER NOT NULL,"+
                    " FOREIGN KEY ($evento_id) REFERENCES ${TabelaBDEvento.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT)"+
                    " $Localidade_id INTEGER NOT NULL, FOREIGN KEY ($Localidade_id) REFERENCES ${TabelaBDLocalidade.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT)")
        }

        companion object {
            const val NOME = "Organizador"
            const val nome_organizador = "Organizador"
            const val Idade = "idade"
            const val telemovel = "telemovel"
            const val Email = "email"
            const val Localidade_id = "Localidade_id"
            const val evento_id = "evento_id"


        }
    }