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

    @Test
    fun consegueInserirTipoEvento() {
        val db = getWritableDatabase()

        insereTipoEvento(db, TipoEventos("Educacao"))

        db.close()
    }

    @Test
    fun consegueInserirOrganizador() {
        val db = getWritableDatabase()

        insereOrganizador(db, Organizador("Vasco","25", "912392239", "vascodasd@gamil.com"))

        db.close()
    }

    @Test
    fun consegueInserirLocalidade() {
        val db = getWritableDatabase()

        insereLocalidade(db, Localidade("Lisboa"))

        db.close()
    }

    @Test
    fun consegueInserirEvento() {
        val db = getWritableDatabase()

        val organizador = Organizador("Vasco", "25", "2321312", "asdasdasd@adasd")
        insereOrganizador(db, organizador)

        val localidade = Localidade("Lisboa")
        insereLocalidade(db, localidade)

        val tipo_evento = TipoEventos("Aventura")
        insereTipoEvento(db, tipo_evento)

        val organizador1 = Organizador("Joao", "23", "919232312", "asdasdasd@adasd")
        insereOrganizador(db, organizador)

        val localidade1 = Localidade("Porto")
        insereLocalidade(db, localidade)

        val tipo_evento1 = TipoEventos("Futebol")
        insereTipoEvento(db, tipo_evento)

        val evento = Evento ("Futebol","23/3/22","Benfica vs Porto",organizador.id,localidade.id,tipo_evento.id)
        insereEvento(db,evento)

        val evento1 = Evento ("docas","23/3/22","animais",organizador1.id,localidade1.id,tipo_evento1.id)
        insereEvento(db,evento1)

        db.close()
    }

    @Test
    fun consegueAlterarLocalidade() {
        val db = getWritableDatabase()

        val localidade = Localidade("LISBOA")
        insereLocalidade(db, localidade)

        localidade.Nome_Localidade = "Aveiro"

        val registosAlterados = TabelaBDLocalidade(db).update(
            localidade.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf("${localidade.id}")
        )

        assertEquals(1, registosAlterados)

        db.close()

    }
    @Test
    fun consegueAlterarOrganizador() {
        val db = getWritableDatabase()

        val organizador = Organizador("Joao","23","91919911","nnajaajaj@jajja.com")
        insereOrganizador(db, organizador)

        organizador.Nome_organizador = "vasco"
        organizador.Telemovel= "919199191"
        organizador.email = "asdas@gasasd.com"
        organizador.idade = "32"

        val registosAlterados = TabelaBDOrganizador(db).update(
            organizador.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf("${organizador.id}"))

        assertEquals(1, registosAlterados)

        db.close()
    }

    @Test
    fun consegueAlterarEvento() {
        val db = getWritableDatabase()

        val organizadorJoao = Organizador("Joao Antonio","23","91919911","nnajaajaj@jajja.com")
        insereOrganizador(db, organizadorJoao)

        val localidadeCoimbra = Localidade("Coimbra")
        insereLocalidade(db, localidadeCoimbra)

        val tipo_eventoEconomina = TipoEventos("Economia")
        insereTipoEvento(db, tipo_eventoEconomina)

        val evento = Evento("Praia","25/07/2022","Peniche",organizadorJoao.id,localidadeCoimbra.id,tipo_eventoEconomina.id)
        insereEvento(db,evento)


        val registosAlterados = TabelaBDEvento(db).update(
            evento.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf("${evento.id}"))

        assertEquals(1, registosAlterados)

        db.close()
    }
    @Test
        fun consegueAlterarTipoEvento() {
        val db = getWritableDatabase()

        val tipoEvento = TipoEventos("Gastronomico")
        insereTipoEvento(db, tipoEvento)

        tipoEvento.tipo_de_evento = "Espacial"

        val registosAlterados = TabelaBDTipoEvento(db).update(
            tipoEvento.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf("${tipoEvento.id}")
        )

        assertEquals(1, registosAlterados)

        db.close()

    }

    @Test
    fun consegueEliminarTipoEvento() {
        val db = getWritableDatabase()

        val tipoevento = TipoEventos("tecnologico")
        insereTipoEvento(db, tipoevento)

        val registosEliminados = TabelaBDTipoEvento(db).delete(
            "${BaseColumns._ID}=?",
            arrayOf("${tipoevento.id}"))

        assertEquals(1, registosEliminados)

        db.close()
    }
    @Test
    fun consegueEliminarEvento() {
        val db = getWritableDatabase()

        val organizador = Organizador("Joao","34","91919911","nnajaajaj@jajja.com")
        insereOrganizador(db, organizador)

        val localidade = Localidade("Coimbra")
        insereLocalidade(db, localidade)

        val tipoevento = TipoEventos("tecnologico")
        insereTipoEvento(db, tipoevento)

        val evento = Evento("Praia","25/07/2022","Peniche",organizador.id,localidade.id,tipoevento.id)
        insereEvento(db,evento)


        val registosEliminados = TabelaBDEvento(db).delete(
            "${BaseColumns._ID}=?",
            arrayOf("${tipoevento.id}"))

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueEliminarLocalidade() {
        val db = getWritableDatabase()

        val localidade = Localidade("Porto")
        insereLocalidade(db, localidade)

        val registosEliminados = TabelaBDLocalidade(db).delete(
            "${BaseColumns._ID}=?",
            arrayOf("${localidade.id}"))

        assertEquals(1, registosEliminados)

        db.close()
    }
    @Test
    fun consegueLerLocalidades() {
        val db = getWritableDatabase()

        val localidade = Localidade("Aveiro")
        insereLocalidade(db, localidade)

        val cursor = TabelaBDLocalidade(db).query(
            TabelaBDLocalidade.TODAS_COLUNAS,
            "${TabelaBDLocalidade.CAMPO_ID}=?",
            arrayOf("${localidade.id}"),
            null,
            null,
            null)


        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        val localidadeBD = Localidade.fromCursor(cursor)
        assertEquals(localidade, localidadeBD)

        db.close()
    }
    @Test
    fun consegueLerTipoEvento() {
        val db = getWritableDatabase()

        val tipoEvento = TipoEventos("Educacao")
        insereTipoEvento(db, tipoEvento)

        val cursor = TabelaBDTipoEvento(db).query(
            TabelaBDTipoEvento.TODAS_COLUNAS,
            "${TabelaBDTipoEvento.CAMPO_ID}=?",
            arrayOf("${tipoEvento.id}"),
            null,
            null,
            null)


        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        val tipoEventoBD = TipoEventos.fromCursor(cursor)
        assertEquals(tipoEvento, tipoEventoBD)

        db.close()
    }
        @Test
        fun consegueLerOrganizador() {
            val db = getWritableDatabase()

            val organizador= Organizador("Vasco","23","91919292","asdasd@sadas")
            insereOrganizador(db, organizador)

            val cursor = TabelaBDOrganizador(db).query(
                TabelaBDOrganizador.TODAS_COLUNAS,
                "${TabelaBDOrganizador.CAMPO_ID}=?",
                arrayOf("${organizador.id}"),
                null,
                null,
                null
            )
            assertEquals(1, cursor.count)
            assertTrue(cursor.moveToNext())

            val organizadorBD = Organizador.fromCursor(cursor)
            assertEquals(organizador, organizadorBD)

            db.close()
        }
    @Test
    fun consegueLerEvento() {
        val db = getWritableDatabase()

        val organizador = Organizador("Vasco","32","232131213","asdasdsad@")
        insereOrganizador(db,organizador)

        val localidade = Localidade("Lisboa")
        insereLocalidade(db, localidade)

        val tipoevento = TipoEventos("Musica")
        insereTipoEvento(db,tipoevento)

        val evento = Evento("Ro ck on Rio","23/12/22","musica",organizador.id,localidade.id,tipoevento.id)
        insereEvento(db,evento)

        val cursor = TabelaBDEvento(db).query(
            TabelaBDEvento.TODAS_COLUNAS,
            "${TabelaBDEvento.CAMPO_ID}=?",
            arrayOf("${evento.id}"),
            null,
            null,
            null
        )

        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        val eventoBD = Evento.fromCursor(cursor)
        assertEquals(evento, eventoBD)

        db.close()
    }

}














