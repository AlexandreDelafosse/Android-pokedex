package com.example.android_pokedex

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        //recyclerView.adapter = PokemonAdapter(pokemons)
        //recyclerView.layoutManager = LinearLayoutManager(this)

        // Configuration du RecyclerView et de l'adapter
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PokemonAdapter(getPokemonList())
        recyclerView.adapter = adapter
    }

    private fun getPokemonList(): List<Pokemon> {
        return listOf(
            Pokemon("Pikachu", "pikachu_image", "Description de Pikachu"),
            Pokemon("Bulbasaur", "bulbasaur_image", "Description de Bulbasaur"),
            Pokemon("Charmander", "charmander_image", "Description de Charmander")
        )
    }
    inner class PokemonAdapter(private val pokemons: List<Pokemon>) :
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
                nameTextView.text = pokemon.nom
                // Utilisez une bibliothèque comme Picasso ou Glide pour charger l'image à partir de l'URL
                // imageView.load(pokemon.image)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, PokemonDetailActivity::class.java)
                    intent.putExtra("pokemon", pokemon)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}