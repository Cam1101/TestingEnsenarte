package com.puj.proyectoensenarte.dictionary.testing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.puj.proyectoensenarte.R

class AlphabetAdapterTesting(
    private val letters: List<String>,
    private val onLetterClick: (String) -> Unit
) : RecyclerView.Adapter<AlphabetAdapterTesting.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivLetter: ImageView = view.findViewById(R.id.ivLetter)
        val tvLetter: TextView = view.findViewById(R.id.tvLetter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_alphabet, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val letter = letters[position]
        holder.tvLetter.text = letter

        // Usa una imagen predeterminada local o placeholder en lugar de Firebase
        holder.ivLetter.setImageResource(R.drawable.inteligencia)  // Coloca aquí la imagen por defecto

        // Configurar el clic en el elemento
        holder.itemView.setOnClickListener {
            onLetterClick(letter)
        }
    }

    override fun getItemCount() = letters.size
}
