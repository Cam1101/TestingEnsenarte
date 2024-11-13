package com.puj.proyectoensenarte.dictionary.testing

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.ActivityDictionaryTestingBinding
import com.puj.proyectoensenarte.dictionary.DetallePorCategoriaActivity
import com.puj.proyectoensenarte.dictionary.DetallePorLetraActivity

class DictionaryTestingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDictionaryTestingBinding

    private val searchHelper = SearchHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDictionaryTestingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupRecyclerViews()
    }

    private fun setupUI() {
        binding.tvDiccionario.text = "Diccionario"

        binding.ivSearch.setOnClickListener {
            val query = binding.etSearch.text.toString().trim()
            val searchMessage = searchHelper.performSearch(query)
            binding.tvSearchMessage.text = searchMessage
            binding.tvSearchMessage.visibility = View.VISIBLE
        }
    }

    private fun setupRecyclerViews() {
        // Código para configurar RecyclerView...
    }

    // Navegación simulada
    fun navigateToDetallePorCategoria(category: String, imageUrl: String) {
        val intent = Intent(this, DetallePorCategoriaActivity::class.java).apply {
            putExtra("CATEGORIA", category)
            putExtra("CATEGORIA_IMAGE_URL", imageUrl)
        }
        startActivity(intent)
    }

    fun navigateToDetallePorLetra(letter: String) {
        val intent = Intent(this, DetallePorLetraActivity::class.java).apply {
            putExtra("LETRA", letter)
        }
        startActivity(intent)
    }
}
