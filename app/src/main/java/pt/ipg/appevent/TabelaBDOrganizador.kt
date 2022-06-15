package pt.ipg.appevent

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDOrganizador(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
            db.execSQL("CREATE TABLE $nome(${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $NOME_ORGANIZADOR TEXT NOT NULL," +
                    " $IDADE TEXT NOT NULL, $TELEMOVEL TEXT NOT NULL, $EMAIL TEXT NOT NULL)")

        }

        companion object {
            const val NOME = "Organizador"
            const val NOME_ORGANIZADOR = "Organizador"
            const val IDADE = "idade"
            const val TELEMOVEL = "telemovel"
            const val EMAIL = "email"


        }
    }