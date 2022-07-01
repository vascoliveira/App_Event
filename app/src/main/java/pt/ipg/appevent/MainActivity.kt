package pt.ipg.appevent

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import pt.ipg.appevent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    var idMenuAtual = R.menu.menu_main
        get() = field
        set(value) {
            if (value != field) {
                field = value
                invalidateOptionsMenu()
            }
        }

    var menu: Menu? = null

    var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main_activity_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

        override fun onCreateOptionsMenu(menu: Menu): Boolean {
            // Inflate the menu; this adds items to the action bar if it is present.
            menuInflater.inflate(idMenuAtual, menu)
            this.menu = menu
            return true
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.

            val opcaoProcessada: Boolean

            if (fragment is MenuPrincipalFragment) {
                opcaoProcessada = (fragment as MenuPrincipalFragment).processaOpcaoMenu(item)
            } else if (fragment is ListarEventosFragment) {
                opcaoProcessada = (fragment as ListarEventosFragment).processaOpcaoMenu(item)
            } else if (fragment is EliminarEventoFragment) {
                opcaoProcessada = (fragment as EliminarEventoFragment).processaOpcaoMenu(item)
            }else if (fragment is ListarOrganizadorFragment) {
                    opcaoProcessada = (fragment as ListarOrganizadorFragment).processaOpcaoMenu(item)
            }else if (fragment is EditarOrganizadorFragment) {
                    opcaoProcessada = (fragment as EditarOrganizadorFragment).processaOpcaoMenu(item)
            } else if (fragment is EliminarOrganizadorFragment) {
                    opcaoProcessada = (fragment as EliminarOrganizadorFragment).processaOpcaoMenu(item)
            } else {
                opcaoProcessada = false
            }

            if (opcaoProcessada) return true

            return super.onOptionsItemSelected(item)
        }



    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main_activity_fragment)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
    fun mostraOpcoesAlterarEliminar(mostra: Boolean) {
        menu!!.findItem(R.id.action_alterar).setVisible(mostra)
        menu!!.findItem(R.id.action_eliminar).setVisible(mostra)
    }

    fun atualizaEvento(id_string_titulo: Int) {
        binding.toolbar.setTitle(id_string_titulo)
    }
}