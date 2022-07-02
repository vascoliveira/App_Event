package pt.ipg.appevent

import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pt.ipg.appevent.ContentProviderEventos
import pt.ipg.appevent.databinding.FragmentEliminarOrganizadorBinding
import com.google.android.material.snackbar.Snackbar

class EliminarOrganizadorFragment : Fragment() {
    private var _binding: FragmentEliminarOrganizadorBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var organizador: Organizador

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminarOrganizadorBinding.inflate(inflater, container, false)
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
        activity.idMenuAtual = R.menu.menu_eliminar

        organizador = EliminarOrganizadorFragmentArgs.fromBundle(arguments!!).organizador

        binding.textViewOrganizador.text = organizador.nomeOrganizador
        binding.textViewIdadeOrganizador.text = organizador.idade
        binding.textViewTelemovelOrganizador.text = organizador.telemovel
        binding.textViewEmailOrganizador.text = organizador.email

    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_eliminar -> {
                eliminaOrganizador()
                true
            }
            R.id.action_cancelar -> {
                voltaListaOrganizador()
                true
            }
            else -> false
        }

    private fun eliminaOrganizador() {
        val alertDialog = AlertDialog.Builder(requireContext())

        alertDialog.apply {
            setTitle(R.string.delete_organizador)
            setMessage(R.string.sure)
            setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialogInterface, i ->  })
            setPositiveButton(R.string.delete, DialogInterface.OnClickListener { dialogInterface, i -> confirmaEliminarOrganizador() })
            show()
        }
    }

    private fun confirmaEliminarOrganizador() {
        val enderecoOrganizador = Uri.withAppendedPath(ContentProviderEventos.ENDERECO_ORGANIZADOR, "${organizador.id}")
        val registosEliminados = requireActivity().contentResolver.delete(enderecoOrganizador, null, null)

        if (registosEliminados != 1) {
            Snackbar.make(
                binding.textViewOrganizador,
                R.string.erro,
                Snackbar.LENGTH_INDEFINITE
            ).show()
            return
        }

        Toast.makeText(requireContext(), R.string.done, Toast.LENGTH_LONG).show()
        voltaListaOrganizador()
    }

    private fun voltaListaOrganizador() {
        val acao = EliminarOrganizadorFragmentDirections.actionEliminarOrganizadorFragmentToSecondFragment()
        findNavController().navigate(acao)
    }
}