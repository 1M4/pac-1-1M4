package edu.uoc.android.pec1

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem

/**
 * Activity para mostrar el detalle de un libro.
 * Solo se usa cuando la pantalla es pequeña
 */
class BookDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)
        setSupportActionBar(findViewById(R.id.detail_toolbar))

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don"t need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            /**
             * Crea el fragmento detalle y lo añade a la activity
             */
            val fragment = BookDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(BookDetailFragment.ARG_ITEM_ID,
                            intent.getStringExtra(BookDetailFragment.ARG_ITEM_ID))
                }
            }
            supportFragmentManager.beginTransaction()
                    .add(R.id.book_detail_container, fragment)
                    .commit()
        }
    }

    /**
     * Define el comportamiento cuando se pulsa el botón atrás
     * En este caso se vuelve a mostrar la activity de la lista de libros
     */
    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    // This ID represents the Home or Up button. In the case of this
                    // activity, the Up button is shown. For
                    // more details, see the Navigation pattern on Android Design:
                    //
                    // http://developer.android.com/design/patterns/navigation.html#up-vs-back

                    navigateUpTo(Intent(this, BookListActivity::class.java))
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
}