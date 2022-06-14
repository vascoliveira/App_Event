package pt.ipg.appevent

import android.database.sqlite.SQLiteDatabase
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

    private fun insereEvento(db: SQLiteDatabase, Evento: Evento) {
        Evento.id = TabelaBDEvento(db).insert(Evento.toContentValues())
        assertNotEquals(-1, Evento.id)
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






