package com.example.android_pokedex

// PokemonAdapter.kt
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PokemonAdapter(private val pokemons: List<Pokemon>) :
    RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pokemon, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = pokemons[position]
        holder.bind(pokemon)
    }

    override fun getItemCount(): Int {
        return pokemons.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(pokemon: Pokemon) {
            nameTextView.text = pokemon.name.capitalize()

            val resourceId = itemView.context.resources.getIdentifier(pokemon.image, "drawable", itemView.context.packageName)
            imageView.setImageResource(resourceId)


            itemView.setOnClickListener {
                val intent = Intent(itemView.context, PokemonDetailActivity::class.java)
                intent.putExtra("pokemon", pokemon)
                itemView.context.startActivity(intent)
            }
        }
    }
}
