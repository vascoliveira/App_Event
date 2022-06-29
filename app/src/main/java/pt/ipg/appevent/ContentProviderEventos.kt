package pt.ipg.appevent

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.provider.BaseColumns

class ContentProviderEventos : ContentProvider() {
    var dbOpenHelper : BdHelper? = null

    /**
     * Implement this to initialize your content provider on startup.
     * This method is called for all registered content providers on the
     * application main thread at application launch time.  It must not perform
     * lengthy operations, or application startup will be delayed.
     *
     *
     * You should defer nontrivial initialization (such as opening,
     * upgrading, and scanning databases) until the content provider is used
     * (via [.query], [.insert], etc).  Deferred initialization
     * keeps application startup fast, avoids unnecessary work if the provider
     * turns out not to be needed, and stops database errors (such as a full
     * disk) from halting application launch.
     *
     *
     * If you use SQLite, [android.database.sqlite.SQLiteOpenHelper]
     * is a helpful utility class that makes it easy to manage databases,
     * and will automatically defer opening until first use.  If you do use
     * SQLiteOpenHelper, make sure to avoid calling
     * [android.database.sqlite.SQLiteOpenHelper.getReadableDatabase] or
     * [android.database.sqlite.SQLiteOpenHelper.getWritableDatabase]
     * from this method.  (Instead, override
     * [android.database.sqlite.SQLiteOpenHelper.onOpen] to initialize the
     * database when it is first opened.)
     *
     * @return true if the provider was successfully loaded, false otherwise
     */
    override fun onCreate(): Boolean {
        dbOpenHelper = BdHelper(context)
        return true
    }

    /**
     * Implement this to handle query requests from clients.
     *
     *
     * Apps targeting [android.os.Build.VERSION_CODES.O] or higher should override
     * [.query] and provide a stub
     * implementation of this method.
     *
     *
     * This method can be called from multiple threads, as described in
     * [Processes
 * and Threads]({@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads).
     *
     *
     * Example client call:
     *
     *
     * <pre>// Request a specific record.
     * Cursor managedCursor = managedQuery(
     * ContentUris.withAppendedId(Contacts.People.CONTENT_URI, 2),
     * projection,    // Which columns to return.
     * null,          // WHERE clause.
     * null,          // WHERE clause value substitution
     * People.NAME + " ASC");   // Sort order.</pre>
     * Example implementation:
     *
     *
     * <pre>// SQLiteQueryBuilder is a helper class that creates the
     * // proper SQL syntax for us.
     * SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();
     *
     * // Set the table we're querying.
     * qBuilder.setTables(DATABASE_TABLE_NAME);
     *
     * // If the query ends in a specific record number, we're
     * // being asked for a specific record, so set the
     * // WHERE clause in our query.
     * if((URI_MATCHER.match(uri)) == SPECIFIC_MESSAGE){
     * qBuilder.appendWhere("_id=" + uri.getPathLeafId());
     * }
     *
     * // Make the query.
     * Cursor c = qBuilder.query(mDb,
     * projection,
     * selection,
     * selectionArgs,
     * groupBy,
     * having,
     * sortOrder);
     * c.setNotificationUri(getContext().getContentResolver(), uri);
     * return c;</pre>
     *
     * @param uri The URI to query. This will be the full URI sent by the client;
     * if the client is requesting a specific record, the URI will end in a record number
     * that the implementation should parse and add to a WHERE or HAVING clause, specifying
     * that _id value.
     * @param projection The list of columns to put into the cursor. If
     * `null` all columns are included.
     * @param selection A selection criteria to apply when filtering rows.
     * If `null` then all rows are included.
     * @param selectionArgs You may include ?s in selection, which will be replaced by
     * the values from selectionArgs, in order that they appear in the selection.
     * The values will be bound as Strings.
     * @param sortOrder How the rows in the cursor should be sorted.
     * If `null` then the provider is free to define the sort order.
     * @return a Cursor or `null`.
     */
    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        requireNotNull(projection)

        val db = dbOpenHelper!!.readableDatabase

        val colunas = projection as Array<String>
        val selArgs = selectionArgs as Array<String>?

        val id = uri.lastPathSegment

        return when (getUriMatcher().match(uri)) {
            URI_Evento -> TabelaBDEvento(db).query(colunas, selection, selArgs,null,null, sortOrder)
            URI_Evento_Especifico -> TabelaBDEvento(db).query(colunas, "${BaseColumns._ID}=?", arrayOf("$id"), null, null, null)
            URI_tipoEvento -> TabelaBDTipoEvento(db).query(colunas, selection, selArgs, null, null, sortOrder)
            URI_tipoEvento_Especifico -> TabelaBDTipoEvento(db).query(colunas,"${BaseColumns._ID}=?", arrayOf("$id"), null, null, null)
            URI_localidade -> TabelaBDLocalidade(db).query(colunas, selection, selArgs, null, null, sortOrder)
            URI_localidade_especifica -> TabelaBDLocalidade(db).query(colunas,"${BaseColumns._ID}=?", arrayOf("$id"), null, null, null)
            URI_organizador -> TabelaBDOrganizador(db).query(colunas, selection, selArgs, null, null, sortOrder)
            URI_organizador_especifico -> TabelaBDOrganizador(db).query(colunas,"${BaseColumns._ID}=?", arrayOf("$id"), null, null, null)
            else -> null
        }
    }

    /**
     * Implement this to handle requests for the MIME type of the data at the
     * given URI.  The returned MIME type should start with
     * `vnd.android.cursor.item` for a single record,
     * or `vnd.android.cursor.dir/` for multiple items.
     * This method can be called from multiple threads, as described in
     * [Processes
 * and Threads]({@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads).
     *
     *
     * Note that there are no permissions needed for an application to
     * access this information; if your content provider requires read and/or
     * write permissions, or is not exported, all applications can still call
     * this method regardless of their access permissions.  This allows them
     * to retrieve the MIME type for a URI when dispatching intents.
     *
     * @param uri the URI to query.
     * @return a MIME type string, or `null` if there is no type.
     */
    override fun getType(uri: Uri): String? =
        when(getUriMatcher().match(uri)) {
            URI_Evento -> "$MULTIPLOS_REGISTOS/${TabelaBDEvento.NOME}"
            URI_tipoEvento -> "$MULTIPLOS_REGISTOS/${TabelaBDTipoEvento.NOME}"
            URI_localidade -> "$MULTIPLOS_REGISTOS/${TabelaBDLocalidade.NOME}"
            URI_organizador -> "$MULTIPLOS_REGISTOS/${TabelaBDOrganizador.NOME}"

            URI_Evento_Especifico -> "$UNICO_REGISTO/${TabelaBDEvento.NOME}"
            URI_tipoEvento_Especifico -> "$UNICO_REGISTO/${TabelaBDTipoEvento.NOME}"
            URI_localidade_especifica -> "$UNICO_REGISTO/${TabelaBDTipoEvento.NOME}"
            URI_organizador_especifico -> "$UNICO_REGISTO/${TabelaBDTipoEvento.NOME}"

            else -> null
        }

    /**
     * Implement this to handle requests to insert a new row. As a courtesy,
     * call
     * [ notifyChange()][ContentResolver.notifyChange] after inserting. This method can be called from multiple
     * threads, as described in [Processes
 * and Threads](
      {@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads).
     *
     * @param uri The content:// URI of the insertion request.
     * @param values A set of column_name/value pairs to add to the database.
     * @return The URI for the newly inserted item.
     */
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = dbOpenHelper!!.writableDatabase

        requireNotNull(values)

        val id = when (getUriMatcher().match(uri)) {
            URI_Evento -> TabelaBDEvento(db).insert(values)
            URI_tipoEvento -> TabelaBDTipoEvento(db).insert(values)
            URI_localidade -> TabelaBDLocalidade(db).insert(values)
            URI_organizador -> TabelaBDOrganizador(db).insert(values)

            else -> -1
        }

        if (id == -1L) return null

        return Uri.withAppendedPath(uri, "$id")
    }

    /**
     * Implement this to handle requests to delete one or more rows. The
     * implementation should apply the selection clause when performing
     * deletion, allowing the operation to affect multiple rows in a directory.
     * As a courtesy, call
     * [ notifyChange()][ContentResolver.notifyChange] after deleting. This method can be called from multiple
     * threads, as described in [Processes
 * and Threads](
      {@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads).
     *
     *
     * The implementation is responsible for parsing out a row ID at the end of
     * the URI, if a specific row is being deleted. That is, the client would
     * pass in `content://contacts/people/22` and the implementation
     * is responsible for parsing the record number (22) when creating a SQL
     * statement.
     *
     * @param uri The full URI to query, including a row ID (if a specific
     * record is requested).
     * @param selection An optional restriction to apply to rows when deleting.
     * @return The number of rows affected.
     * @throws SQLException
     */
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = dbOpenHelper!!.writableDatabase

        val id = uri.lastPathSegment

        return when (getUriMatcher().match(uri)) {
            URI_tipoEvento_Especifico -> TabelaBDOrganizador(db).delete("${BaseColumns._ID}=?", arrayOf("$id"))
            URI_Evento_Especifico -> TabelaBDEvento(db).delete("${BaseColumns._ID}=?", arrayOf("$id"))
            URI_localidade_especifica -> TabelaBDEvento(db).delete("${BaseColumns._ID}=?", arrayOf("$id"))
            URI_organizador_especifico -> TabelaBDOrganizador(db).delete("${BaseColumns._ID}=?", arrayOf("$id"))
            else -> 0
        }
    }

    /**
     * Implement this to handle requests to update one or more rows. The
     * implementation should update all rows matching the selection to set the
     * columns according to the provided values map. As a courtesy, call
     * [ notifyChange()][ContentResolver.notifyChange] after updating. This method can be called from multiple
     * threads, as described in [Processes
 * and Threads](
      {@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads).
     *
     * @param uri The URI to query. This can potentially have a record ID if
     * this is an update request for a specific record.
     * @param values A set of column_name/value pairs to update in the database.
     * @param selection An optional filter to match rows to update.
     * @return the number of rows affected.
     */
    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        requireNotNull(values)

        val db = dbOpenHelper!!.writableDatabase

        val id = uri.lastPathSegment

        return when (getUriMatcher().match(uri)) {
            URI_tipoEvento_Especifico -> TabelaBDTipoEvento(db).update(values, "${BaseColumns._ID}=?", arrayOf("$id"))
            URI_Evento_Especifico -> TabelaBDEvento(db).update(values,"${BaseColumns._ID}=?", arrayOf("$id"))
            URI_localidade_especifica -> TabelaBDLocalidade(db).update(values,"${BaseColumns._ID}=?", arrayOf("$id"))
            URI_organizador_especifico -> TabelaBDOrganizador(db).update(values,"${BaseColumns._ID}=?", arrayOf("$id"))
            else -> 0
        }
    }

    companion object {
        private const val AUTORIDADE = "pt.ipg.appevent"

        private const val URI_tipoEvento = 100
        private const val URI_tipoEvento_Especifico = 101
        private const val URI_Evento = 200
        private const val URI_Evento_Especifico = 201
        private const val URI_organizador = 300
        private const val URI_organizador_especifico = 301
        private const val URI_localidade = 400
        private const val URI_localidade_especifica = 401


        private const val UNICO_REGISTO = "vnd.android.cursor.item"
        private const val MULTIPLOS_REGISTOS = "vnd.android.cursor.dir"

        private val ENDERECO_BASE = Uri.parse("content://$AUTORIDADE")
        val ENDERECO_EVENTOS = Uri.withAppendedPath(ENDERECO_BASE, TabelaBDEvento.NOME)
        val ENDERECO_TIPOEVENTO = Uri.withAppendedPath(ENDERECO_BASE, TabelaBDTipoEvento.NOME)
        val ENDERECO_LOCALIDADE = Uri.withAppendedPath(ENDERECO_BASE, TabelaBDLocalidade.NOME)
        val ENDERECO_ORGANIZADOR = Uri.withAppendedPath(ENDERECO_BASE, TabelaBDOrganizador.NOME)


        fun getUriMatcher() : UriMatcher {
            val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

            uriMatcher.addURI(AUTORIDADE, TabelaBDEvento.NOME, URI_Evento)
            uriMatcher.addURI(AUTORIDADE, "${TabelaBDEvento.NOME}/#", URI_Evento_Especifico)
            uriMatcher.addURI(AUTORIDADE, TabelaBDTipoEvento.NOME, URI_tipoEvento)
            uriMatcher.addURI(AUTORIDADE, "${TabelaBDTipoEvento.NOME}/#", URI_tipoEvento_Especifico)
            uriMatcher.addURI(AUTORIDADE, TabelaBDOrganizador.NOME, URI_organizador)
            uriMatcher.addURI(AUTORIDADE, "${TabelaBDOrganizador.NOME}/#", URI_organizador_especifico)
            uriMatcher.addURI(AUTORIDADE, TabelaBDLocalidade.NOME, URI_localidade)
            uriMatcher.addURI(AUTORIDADE, "${TabelaBDOrganizador.NOME}/#", URI_localidade_especifica)

            return uriMatcher
        }
    }
}

