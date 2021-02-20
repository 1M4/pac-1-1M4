package edu.uoc.pac1kotlin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.uoc.pac1kotlin.model.BookContent
import kotlinx.android.synthetic.main.activity_book_detail.*
import kotlinx.android.synthetic.main.book_detail.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * A fragment representing a single Book detail screen.
 * This fragment is either contained in a [BookListActivity]
 * in two-pane mode (on tablets) or a [BookDetailActivity]
 * on handsets.
 */
class BookDetailFragment : Fragment() {

    private var mItem: BookContent.BookItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments?.containsKey(ARG_ITEM_ID)!!) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = BookContent.ITEMS[arguments?.getInt(ARG_ITEM_ID)!!]
            mItem?.let {
                activity?.toolbar_layout?.title = it.title
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.book_detail, container, false)

        // Show the book content
        mItem?.let {
            rootView.book_author.text = it.author
            rootView.book_detail.text = it.description
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            rootView.book_date.text = sdf.format(it.publicationDate)
        }

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}
