package edu.uoc.android.pec1

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import edu.uoc.android.pec1.dummy.DummyBooks

/**
 * Fragment que muestra el detalle de un libro
 * Puede estar contenido en la activity [BookListActivity] en el modo dos paneles
 * o en la activity [BookDetailActivity]
 */
class BookDetailFragment : Fragment() {

    /**
     * Objeto libro que se muestra
     */
    private var book: DummyBooks.DummyBook? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * Cargamos los datos del libro a partir del mapa de libros, si hay argumentos
         */
        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                book = DummyBooks.BOOK_MAP[it.getString(ARG_ITEM_ID)]
                activity?.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)?.title = book?.content
            }
        }
    }

    /**
     * Carga y muestra los datos del libro en un TextView
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.book_detail, container, false)

        book?.let {
            rootView.findViewById<TextView>(R.id.book_detail).text = it.details
        }

        return rootView
    }

    companion object {
        /**
         * El argumento de este fragmento es el id del libro a mostrar
         */
        const val ARG_ITEM_ID = "item_id"
    }
}