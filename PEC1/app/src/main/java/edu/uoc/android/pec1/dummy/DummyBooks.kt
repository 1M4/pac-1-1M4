package edu.uoc.android.pec1.dummy

import java.util.ArrayList
import java.util.HashMap

/**
 * Clase para crear un objeto libro de prueba para el ejercicio 1
 */
object DummyBooks {

    /**
     * Lista de libros
     */
    val BOOKS: MutableList<DummyBook> = ArrayList()

    /**
     * Mapa de los libros con su id como key
     */
    val BOOK_MAP: MutableMap<String, DummyBook> = HashMap()

    /**
     * Total de libros
     */
    private val COUNT = 12

    /**
     * Rellena la lista de libros
     */
    init {
        for (i in 1..COUNT) {
            addItem(createDummyBook(i))
        }
    }

    /**
     * Añade un libro a la lista y al mapa
     */
    private fun addItem(book: DummyBook) {
        BOOKS.add(book)
        BOOK_MAP[book.id] = book
    }

    /**
     * Crea el objeto libro y lo devuelve
     */
    private fun createDummyBook(position: Int): DummyBook {
        return DummyBook(position.toString(), "Item $position", makeDetails(position))
    }

    /**
     * Crea los detalles para el objeto libro
     */
    private fun makeDetails(position: Int): String {
        return "Details about book: $position\nMore details information here."
    }

    /**
     * Objeto libro Dummy
     */
    data class DummyBook(val id: String, val content: String, val details: String) {
        override fun toString(): String = content
    }
}