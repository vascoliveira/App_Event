package pt.ipg.appevent

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDOrganizador(db: SQLiteDatabase) : TabelaBD(db, NOME) {
    override fun cria() {
            db.execSQL("CREATE TABLE $nome(${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,$nome_organizador TEXT NOT NULL," +
                    " $Idade TEXT NOT NULL, $telemovel TEXT NOT NULL, $Email TEXT NOT NULL")

        }

        companion object {
            const val NOME = "Organizador"
            const val nome_organizador = "Organizador"
            const val Idade = "idade"
            const val telemovel = "telemovel"
            const val Email = "email"


        }
    }