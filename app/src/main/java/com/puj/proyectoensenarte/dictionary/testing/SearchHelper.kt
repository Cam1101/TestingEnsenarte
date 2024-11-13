package com.puj.proyectoensenarte.dictionary.testing

data class Category(val imageUrl: String, val name: String)

class SearchHelper {

    // Datos simulados
    private val simulatedCategories = listOf(
        Category("https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/imagenesCategorias%2FAseoPersonal.png?alt=media&token=352b7888-2988-498c-9c78-5aea6d0af2f8", "Aseo personal"),
        Category("https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/imagenesCategorias%2FVestuario.png?alt=media&token=ef41cfd5-694d-4333-a872-cc70d47e5d3e", "Vestuario"),
        Category("https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/imagenesCategorias%2FAlimentacion.png?alt=media&token=664f3927-5f2c-46fc-90e8-fa4b6a56ad34", "Alimentación")
    )

    /**
     * Realiza la búsqueda en las categorías simuladas y devuelve el resultado.
     */
    fun performSearch(query: String): String {
        if (query.isEmpty()) {
            return "Por favor, ingrese un término de búsqueda"
        }

        val matchingCategories = simulatedCategories.filter { it.name.contains(query, ignoreCase = true) }
        return if (matchingCategories.isNotEmpty()) {
            "Categorías encontradas: ${matchingCategories.joinToString { it.name }}"
        } else {
            "No se encontraron categorías."
        }
    }
}
