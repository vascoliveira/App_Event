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
import com.google.android.material.snackbar.Snackbar
import pt.ipg.appevent.databinding.FragmentEditarOrganizadorBinding

class EditarOrganizadorFragment : Fragment() {
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
                binding.editTextNomeOrganizador.setText(organizador!!.nomeOrganizador)
                binding.editTextTelemovel.setText(organizador!!.telemovel)
                binding.editTextIdade.setText(organizador!!.idade)
                binding.editTextEmail.setText(organizador!!.email)
            }
        }

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

    fun processaOpcaoMenu(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.action_guardar -> {
                guardar()
                true
            }
            R.id.action_cancelar -> {
                voltaListaOrganizadores()
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

        val contacto = binding.editTextTelemovel.text.toString()
        if (contacto.isBlank()) {
            binding.editTextTelemovel.error = getString(R.string.campo_obrigatorio)
            binding.editTextTelemovel.requestFocus()
            return
        }

        val idade = binding.editTextTelemovel.text.toString()
        if (idade.isBlank()) {
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


        val clienteGuardado =
            if (organizador == null) {
                insereOrganizador(nome, contacto , idade, email )
            } else {
                alteraOrganizador(nome, contacto, idade, email)
            }

        if (clienteGuardado) {
            Toast.makeText(requireContext(), R.string.done, Toast.LENGTH_LONG)
                .show()
            voltaListaOrganizadores()
        } else {
            Snackbar.make(
                binding.editTextNomeOrganizador,
                R.string.erro,
                Snackbar.LENGTH_INDEFINITE
            ).show()
            return
        }
    }

    private fun alteraOrganizador(
        nome: String,
        telemovel: String,
        idade: String,
        email: String
    ): Boolean {
        val organizador = Organizador(nome, idade, telemovel, email)

        val enderecoOrganizadores = Uri.withAppendedPath(
            ContentProviderEventos.ENDERECO_ORGANIZADOR,
            "${this.organizador!!.id}"
        )

        val registosAlterados = requireActivity().contentResolver.update(
            enderecoOrganizadores,
            organizador.toContentValues(),
            null,
            null
        )

        return registosAlterados == 1
    }

    private fun insereOrganizador(
        nome: String,
        telemovel: String,
        idade: String,
        email: String
    ): Boolean {
        val organizador = Organizador(nome, idade, telemovel, email)

        val enderecoOrganizadorInserido = requireActivity().contentResolver.insert(
            ContentProviderEventos.ENDERECO_ORGANIZADOR,
            organizador.toContentValues()
        )

        return enderecoOrganizadorInserido != null
    }

    private fun voltaListaOrganizadores() {
        findNavController().navigate(R.id.action_editarOrganizadorFragment_to_SecondFragment)
    }
}


