package pt.ipg.appevent

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BaseDadosTest {
    private fun appContext() =
        InstrumentationRegistry.getInstrumentation().targetContext

    private fun getWritableDatabase(): SQLiteDatabase {
        val openHelper = BdHelper(appContext())
        return openHelper.writableDatabase
    }

    private fun insereTipoEvento(db: SQLiteDatabase, eventos: TipoEventos) {
        eventos.id = TabelaBDTipoEvento(db).insert(eventos.toContentValues())
        assertNotEquals(-1, eventos.id)
    }

    private fun insereOrganizador(db: SQLiteDatabase, organizador: Organizador) {
        organizador.id = TabelaBDOrganizador(db).insert(organizador.toContentValues())
        assertNotEquals(-1, organizador.id)
    }
    private fun insereLocalidade(db: SQLiteDatabase, localidade: Localidade) {
        localidade.id = TabelaBDLocalidade(db).insert(localidade.toContentValues())
        assertNotEquals(-1, localidade.id)
    }
    private fun insereEvento(db: SQLiteDatabase, evento: Evento) {
        evento.id = TabelaBDEvento(db).insert(evento.toContentValues())
        assertNotEquals(-1, evento.id)
    }


    @Before
    fun apagaBaseDados() {
        appContext().deleteDatabase(BdHelper.NOME)
    }

    @Test
    fun consegueAbrirBaseDados() {
        val openHelper = BdHelper(appContext())
        val db = openHelper.readableDatabase

        assertTrue(db.isOpen)

        db.close()
    }



}







