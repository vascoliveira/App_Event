package pt.ipg.appevent

import android.database.Cursor

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleCursorAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import pt.ipg.appevent.ContentProviderEventos
import pt.ipg.appevent.TipoEventos
import pt.ipg.appevent.TabelaBDTipoEvento
import pt.ipg.appevent.Evento
import pt.ipg.appevent.databinding.FragmentEditarEventoBinding
import com.google.android.material.snackbar.Snackbar


class EditarEventoFragment : Fragment(),LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding: FragmentEditarEventoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var evento: Evento? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarEventoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_edicao

        if (arguments != null) {
            evento = EditarEventoFragmentArgs.fromBundle(arguments!!).evento

            if (evento != null) {
                binding.editTextNomeEvento.setText(evento!!.Nome_Evento)
                binding.editTextOrganizador.setText(evento!!.Data)
                binding.editTextDescricao.setText(evento!!.Descricao)

            }
        }
        LoaderManager.getInstance(this).initLoader(ID_LOADER_TIPOEVENTO,null,this)

    }

    companion object{
        const val ID_LOADER_TIPOEVENTO = 0
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param id The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
     override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> =
        CursorLoader(
            requireContext(),
            ContentProviderEventos.ENDERECO_TIPOEVENTO,
            TabelaBDTipoEvento.TODAS_COLUNAS,
            null,
            null,
            "${TabelaBDTipoEvento.TIPO_EVENTO}"
        )

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is not allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See [ FragmentManager.openTransaction()][androidx.fragment.app.FragmentManager.beginTransaction] for further discussion on this.
     *
     *
     * This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     *
     *
     *  *
     *
     *The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a [android.database.Cursor]
     * and you place it in a [android.widget.CursorAdapter], use
     * the [android.widget.CursorAdapter.CursorAdapter] constructor without passing
     * in either [android.widget.CursorAdapter.FLAG_AUTO_REQUERY]
     * or [android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER]
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     *  *  The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a [android.database.Cursor] from a [android.content.CursorLoader],
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * [android.widget.CursorAdapter], you should use the
     * [android.widget.CursorAdapter.swapCursor]
     * method so that the old Cursor is not closed.
     *
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */
    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        val adapterTipoEvento = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaBDTipoEvento.TIPO_EVENTO),
            intArrayOf(android.R.id.text1),
            0
        )

        binding.spinnerTipoEventos.adapter = adapterTipoEvento

        atualizaTipoEventoSelecionado()
    }

    private fun atualizaTipoEventoSelecionado() {
        if (evento == null) return
        val idTipoEventos = evento!!.tipo_eventos.id

        val ultimoTipoEventos = binding.spinnerTipoEventos.count - 1

        for (i in 0..ultimoTipoEventos) {
            if (binding.spinnerTipoEventos.getItemIdAtPosition(i) == idTipoEventos) {
                binding.spinnerTipoEventos.setSelection(i)
                return
            }
        }
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */
    override fun onLoaderReset(loader: Loader<Cursor>) {
        if (_binding == null) return
        binding.spinnerTipoEventos.adapter = null
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.action_guardar -> {
                guardar()
                true
            }
            R.id.action_cancelar -> {
                voltaListaEventos()
                true
            }
            else -> false
        }

    private fun guardar() {
        val nome = binding.editTextNomeEvento.text.toString()
        if (nome.isBlank()) {
            binding.editTextNomeEvento.error = getString(R.string.campo_obrigatorio)
            binding.editTextNomeEvento.requestFocus()
            return
        }

        val data = binding.editTextOrganizador.text.toString()
        if (data.isBlank()) {
            binding.editTextOrganizador.error = getString(R.string.campo_obrigatorio)
            binding.editTextOrganizador.requestFocus()
            return
        }

        val descricao = binding.editTextDescricao.text.toString()
        if (descricao.isBlank()) {
            binding.editTextDescricao.error = getString(R.string.campo_obrigatorio)
            binding.editTextDescricao.requestFocus()
            return
        }
        val tipoEvento = binding.spinnerTipoEventos.selectedItemId
        if (tipoEvento == Spinner.INVALID_ROW_ID) {
            binding.textView5.error = getString(R.string.field_mandatory)
            binding.spinnerTipoEventos.requestFocus()
            return
        }

        val eventoGuardado =
            if (evento == null) {
                insereEvento(nome,data,descricao,tipoEvento)
            } else {
                alteraEvento(nome,data,descricao,tipoEvento)
            }

        if (eventoGuardado) {
            Toast.makeText(requireContext(), R.string.done, Toast.LENGTH_LONG)
                .show()
            voltaListaEventos()
        } else {
            Snackbar.make(
                binding.editTextNomeEvento,
                R.string.erro,
                Snackbar.LENGTH_INDEFINITE
            ).show()
            return
        }
    }

    private fun alteraEvento(
        nome: String,
        data: String,
        descricao: String,
        idTipoEventos: Long,
    ): Boolean {
        val evento = Evento(nome, data,descricao,2,1,TipoEventos( id= idTipoEventos))

        val enderecoEvento = Uri.withAppendedPath(
            ContentProviderEventos.ENDERECO_EVENTOS,
            "${this.evento!!.id}"
        )

        val registosAlterados = requireActivity().contentResolver.update(
            enderecoEvento,
            evento.toContentValues(),
            null,
            null
        )

        return registosAlterados == 1
    }

    private fun insereEvento(
        nome: String,
        data: String,
        descricao: String,
        idTipoEventos: Long,

    ): Boolean {

        val evento = Evento(nome,data,descricao,1,2,TipoEventos( id= idTipoEventos))

        val enderecoEventoInserido = requireActivity().contentResolver.insert(
            ContentProviderEventos.ENDERECO_EVENTOS,
            evento.toContentValues()
        )

        return enderecoEventoInserido != null
    }

    private fun voltaListaEventos() {
        findNavController().navigate(R.id.action_editarEventoFragment_to_listarEventos)
    }
}
