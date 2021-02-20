package edu.uoc.pac1kotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.design.widget.Snackbar
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

import edu.uoc.pac1kotlin.model.BookContent
import jp.wasabeef.picasso.transformations.BlurTransformation
import jp.wasabeef.picasso.transformations.GrayscaleTransformation
import kotlinx.android.synthetic.main.activity_book_list.*

import kotlinx.android.synthetic.main.book_list.*
import kotlinx.android.synthetic.main.book_list_content_stag.view.*

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [BookDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class BookListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var mTwoPane: Boolean = false
    private var adapter: SimpleItemRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        if (book_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true
        } else {
            val staggeredManager: StaggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            book_list.layoutManager = staggeredManager
        }

        setupRecyclerView(book_list)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        adapter = SimpleItemRecyclerViewAdapter(this,  BookContent.ITEMS, mTwoPane)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.by_title -> {
                adapter!!.setItems(BookContent.ITEMS.sortedWith(compareBy({ it.title })))
                true
            }
            R.id.by_author -> {
                adapter!!.setItems(BookContent.ITEMS.sortedWith(compareBy({ it.author })))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    class SimpleItemRecyclerViewAdapter(private val mParentActivity: BookListActivity,
                                        private var mValues: List<BookContent.BookItem>,
                                        private val mTwoPane: Boolean) :
            RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        companion object {
            const val EVEN = 0
            const val ODD = 1
            const val STAGGERED = 2
        }

        private val mOnClickListener: View.OnClickListener

        init {
            mOnClickListener = View.OnClickListener { v ->
                val item = v.tag as BookContent.BookItem
                if (mTwoPane) {
                    val fragment = BookDetailFragment().apply {
                        arguments = Bundle()
                        arguments!!.putInt(BookDetailFragment.ARG_ITEM_ID, item.idBook)
                    }
                    mParentActivity.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.book_detail_container, fragment)
                            .commit()
                } else {
                    val intent = Intent(v.context, BookDetailActivity::class.java).apply {
                        putExtra(BookDetailFragment.ARG_ITEM_ID, item.idBook)
                    }
                    v.context.startActivity(intent)
                }
            }
        }

        fun setItems(items: List<BookContent.BookItem>) {
            mValues = items
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view: View = when (viewType) {
                EVEN -> {
                    LayoutInflater.from(parent.context)
                            .inflate(R.layout.book_list_content, parent, false)
                }
                ODD -> {
                    LayoutInflater.from(parent.context)
                            .inflate(R.layout.book_list_content_odd, parent, false)
                }
                else -> {
                    LayoutInflater.from(parent.context)
                            .inflate(R.layout.book_list_content_stag, parent, false)
                }
            }
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = mValues[position]
            holder.mTitleView.text = item.title
            holder.mAuthorView.text = item.author
            if (!mTwoPane) {
                val blurTransformation = BlurTransformation(mParentActivity, 5, 10)
                if (position % 2 == 0) {
                    Picasso.with(mParentActivity).load(R.drawable.el_juego_de_ender)
                            .transform(blurTransformation)
                            .transform(GrayscaleTransformation())
                            .into(holder.mImageView)
                } else {
                    Picasso.with(mParentActivity).load(R.drawable.matilda)
                            .transform(blurTransformation)
                            .transform(GrayscaleTransformation())
                            .into(holder.mImageView)
                }
            }

            with(holder.itemView) {
                tag = item
                setOnClickListener(mOnClickListener)
            }
        }

        override fun getItemCount(): Int {
            return mValues.size
        }

        override fun getItemViewType(position: Int): Int {
            return if (!mTwoPane) {
                STAGGERED
            } else {
                if (position % 2 == 0) {
                    EVEN
                } else {
                    ODD
                }
            }
        }

        inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
            val mTitleView: TextView = mView.title
            val mAuthorView: TextView = mView.author
            val mImageView: ImageView = mView.image_book
        }
    }
}
