package pt.ipg.appevent

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BdHelper (context: Context?) : SQLiteOpenHelper(context, NOME, null, VERSAO) {
    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    override fun onCreate(db: SQLiteDatabase?) {
        requireNotNull(db)

        TabelaBDOrganizador(db).cria()
        TabelaBDEvento(db).cria()
        TabelaBDLocalidade(db).cria()
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    companion object {
        const val NOME = "AppEvent.db"
        private const val VERSAO = 1
    }
}