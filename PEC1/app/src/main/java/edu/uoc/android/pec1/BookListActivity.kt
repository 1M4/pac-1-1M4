package edu.uoc.android.pec1

import android.content.Intent
import android.os.Bundle
import androidx.core.widget.NestedScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import edu.uoc.android.pec1.dummy.DummyBooks

/**
 * Activity para presentar una lista de libros.
 * Si la pantalla es suficientemente grande, se mostrará la lista + el detalle
 * de cada libro seleccionado, sino, solo la lista
 */
class BookListActivity : AppCompatActivity() {

    /**
     * Variable para controlar si se van a mostar lista + detalle en la misma pantalla
     * o solo la lista
     */
    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title

        /**
         * Comprobamos si se muestra la vista del detalle, en ese caso es que la pantalla
         * es suficientemente grande y la variable twoPane debe ser true
         */
        if (findViewById<NestedScrollView>(R.id.book_detail_container) != null) {
            twoPane = true
        }

        setupRecyclerView(findViewById(R.id.book_list))
    }

    /**
     * Prepara el RecyclerView
     */
    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, DummyBooks.BOOKS, twoPane)
    }

    /**
     * Clase para cada item del RecyclerView
     */
    class SimpleItemRecyclerViewAdapter(private val parentActivity: BookListActivity,
                                        private val values: List<DummyBooks.DummyBook>,
                                        private val twoPane: Boolean) :
            RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        /**
         * Definimos el comportamiento al clickar en un item de la lista
         */
        private val onClickListener: View.OnClickListener = View.OnClickListener { v ->
            val item = v.tag as DummyBooks.DummyBook
            /**
             * Si la pantalla es grande, se pondrá el detalle del item seleccionado
             * sobre el fragment
             * Mientras que si la pantalla es pequeña, se hará un startActivity
             * para mostrar ese fragment sustituyendo a la lista
             */
            if (twoPane) {
                val fragment = BookDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(BookDetailFragment.ARG_ITEM_ID, item.id)
                    }
                }
                parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.book_detail_container, fragment)
                        .commit()
            } else {
                val intent = Intent(v.context, BookDetailActivity::class.java).apply {
                    putExtra(BookDetailFragment.ARG_ITEM_ID, item.id)
                }
                v.context.startActivity(intent)
            }
        }

        /**
         * Crea el ViewHolder de la lista de libros
         */
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.book_list_content, parent, false)
            return ViewHolder(view)
        }

        /**
         * Define el contenido del ViewHolder y su comportamiento al clickar sobre cada libro
         * de la lista
         */
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.idView.text = item.id
            holder.contentView.text = item.content

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        /**
         * Devuelve el tamaño de la lista de libros
         */
        override fun getItemCount() = values.size

        /**
         * Clase interna para definir los objetos de la vista de la lista
         */
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val idView: TextView = view.findViewById(R.id.id_text)
            val contentView: TextView = view.findViewById(R.id.content)
        }
    }
}