package pt.ipg.appevent

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView




class AdapterOrganizador (val fragment: ListarOrganizadorFragment): RecyclerView.Adapter<AdapterOrganizador.ViewHolderOrganizador>() {
    var cursor: Cursor? = null
        get() = field
        set(value) {
            if(field != value){
                field = value
                notifyDataSetChanged()
            }
        }



    class ViewHolderOrganizador(itemOrganizador: View) : RecyclerView.ViewHolder(itemOrganizador) {
        val textViewNomeOrganizador  = itemOrganizador.findViewById<TextView>(R.id.textViewNome)
        val textViewIdade = itemOrganizador.findViewById<TextView>(R.id.textViewIdade)
        val textViewTelemovel = itemOrganizador.findViewById<TextView>(R.id.textViewTelemovel)
        val textViewEmail = itemOrganizador.findViewById<TextView>(R.id.textViewEmail)

        var organizador : Organizador? = null
            get() = field
            set(value: Organizador?) {
                field = value

                textViewNomeOrganizador.text = organizador?.Nome_organizador ?: ""
                textViewIdade.text = (organizador?.idade?: "").toString()
                textViewTelemovel.text = organizador?.Telemovel ?: ""
                textViewEmail.text = organizador?.email ?: ""
            }
    }

    /**
     * Called when RecyclerView needs a new [ViewHolder] of the given type to represent
     * an item.
     *
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     *
     *
     * The new ViewHolder will be used to display items of the adapter using
     * [.onBindViewHolder]. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary [View.findViewById] calls.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     * an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return A new ViewHolder that holds a View of the given view type.
     * @see .getItemViewType
     * @see .onBindViewHolder
     */
    //override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderLivro {
    // val itemCliente = fragment.layoutInflater.inflate(R.layout.activity_client_update_delete, parent, false)
    //return ViewHolderLivro(itemCliente)

    //}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderOrganizador {
        val itemOrganizador = fragment.layoutInflater.inflate(R.layout.item_organizador, parent, false)
        return ViewHolderOrganizador(itemOrganizador)
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the [ViewHolder.itemView] to reflect the item at the given
     * position.
     *
     *
     * Note that unlike [android.widget.ListView], RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the `position` parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use [ViewHolder.getAdapterPosition] which will
     * have the updated adapter position.
     *
     * Override [.onBindViewHolder] instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     * item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: ViewHolderOrganizador, position: Int) {
        cursor!!.moveToPosition(position)
        holder.organizador= Organizador.fromCursor(cursor!!)
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {
        if(cursor == null) return 0

        return cursor!!.count
    }


}