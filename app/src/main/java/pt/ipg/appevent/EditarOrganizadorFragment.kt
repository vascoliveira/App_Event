package pt.ipg.appevent

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import pt.ipg.appevent.Organizador
import pt.ipg.appevent.ContentProviderEventos
import com.google.android.material.snackbar.Snackbar
import pt.ipg.appevent.databinding.FragmentEditarOrganizadorBinding

class EditarOrganizadorFragment : Fragment(){
    private var _binding: FragmentEditarOrganizadorBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var organizador: Organizador? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarOrganizadorBinding.inflate(inflater, container, false)
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
            organizador = EditarOrganizadorFragmentArgs.fromBundle(arguments!!).organizador

            if (organizador != null) {
                binding.editTextNomeOrganizador.setText(organizador!!.Nome_organizador)
                binding.editTextIdade.setText(organizador!!.idade)
                binding.editTextTelemovel.setText(organizador!!.Telemovel)
                binding.editTextEmail.setText(organizador!!.email)
            }
        }

    }

    companion object {
        const val ID_LOADER_ORGAZINADOR = 0
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

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_guardar -> {
                guardar()
                true
            }
            R.id.action_cancelar -> {
                voltaListaOrganizador()
                true
            }
            else -> false
        }

    private fun guardar() {
        val nome = binding.editTextNomeOrganizador.text.toString()
        if (nome.isBlank()) {
            binding.editTextNomeOrganizador.error = getString(R.string.campo_obrigatorio)
            binding.editTextNomeOrganizador.requestFocus()
            return
        }
        val idade = binding.editTextIdade.text.toString()
        if (idade.isBlank()) {
            binding.editTextIdade.error = getString(R.string.campo_obrigatorio)
            binding.editTextIdade.requestFocus()
            return
        }

        val contacto= binding.editTextTelemovel.text.toString()
        if (contacto.isBlank()) {
            binding.editTextTelemovel.error = getString(R.string.campo_obrigatorio)
            binding.editTextTelemovel.requestFocus()
            return
        }

        val email = binding.editTextEmail.text.toString()
        if (email.isBlank()) {
            binding.editTextEmail.error = getString(R.string.campo_obrigatorio)
            binding.editTextEmail.requestFocus()
            return
        }


        val organizadorGuardado =
            if (organizador== null) {
                insereOrganizador(nome,idade,contacto,email)
            } else {
                alteraOrganizador(nome,idade, contacto, email)
            }

        if (organizadorGuardado) {
            Toast.makeText(requireContext(),R.string.done, Toast.LENGTH_LONG)
                .show()
            voltaListaOrganizador()
        } else {
            Snackbar.make(binding.editTextNomeOrganizador, R.string.erro, Snackbar.LENGTH_INDEFINITE).show()
            return
        }
    }

    private fun alteraOrganizador(nome : String, idade : String, telemovel : String, email : String) : Boolean {

        val organizador = Organizador(nome,idade,telemovel,email)

        val enderecoOrganizador  = Uri.withAppendedPath(ContentProviderEventos.ENDERECO_ORGANIZADOR, "${this.organizador!!.id}")

        val registosAlterados = requireActivity().contentResolver.update(enderecoOrganizador, organizador.toContentValues(),null,null )

        return registosAlterados == 1
    }

    private fun insereOrganizador(nome : String, idade : String,  telemovel : String, email : String ): Boolean {

        val organizador = Organizador(nome, idade,telemovel,email)

        val OrganizadorInserido = requireActivity().contentResolver.insert(ContentProviderEventos.ENDERECO_ORGANIZADOR, organizador.toContentValues())

        return OrganizadorInserido != null
    }

    private fun voltaListaOrganizador() {
        findNavController().navigate(R.id.action_editarOrganizadorFragment_to_SecondFragment)
    }


}