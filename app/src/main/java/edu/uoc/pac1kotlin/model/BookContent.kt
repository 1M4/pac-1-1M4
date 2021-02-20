package edu.uoc.pac1kotlin.model

import java.util.*

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 */
object BookContent {

    /**
     * An array of sample book items.
     */
    var ITEMS: MutableList<BookItem> = ArrayList()


    init {
        val book1: BookItem = BookItem(0, "Book1", "Author1", Date(), "Description1", "")
        val book2: BookItem = BookItem(1, "Book4", "Author3", Date(), "Description2", "")
        val book11: BookItem = BookItem(2, "Book3", "Author5", Date(), "Description1", "")
        val book12: BookItem = BookItem(4, "Book7", "Author6", Date(), "Description1", "")
        val book22: BookItem = BookItem(5, "Book6", "Author7", Date(), "Description2", "")
        val book13: BookItem = BookItem(6, "Book5", "Author8", Date(), "Description1", "")
        val book23: BookItem = BookItem(7, "Book8", "Author11", Date(), "Description2", "")
        val book14: BookItem = BookItem(8, "Book13", "Author9", Date(), "Description1", "")
        val book24: BookItem = BookItem(9, "Book20", "Author20", Date(), "Description2", "")
        val book15: BookItem = BookItem(10, "Book11", "Author4", Date(), "Description1", "")
        val book25: BookItem = BookItem(11, "Book12", "Author20", Date(), "Description2", "")
        ITEMS.add(book1)
        ITEMS.add(book2)
        ITEMS.add(book11)
        ITEMS.add(book12)
        ITEMS.add(book12)
        ITEMS.add(book22)
        ITEMS.add(book13)
        ITEMS.add(book23)
        ITEMS.add(book14)
        ITEMS.add(book24)
        ITEMS.add(book15)
        ITEMS.add(book25)

    }


    /**
     * Book item with detail fields
     */
    data class BookItem(val idBook: Int, val title: String, val author: String,
                         val publicationDate: Date, val description: String, val urlImage: String) {
        override fun toString(): String = description
    }
}
