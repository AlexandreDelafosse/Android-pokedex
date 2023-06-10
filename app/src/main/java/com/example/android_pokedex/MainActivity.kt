package com.example.android_pokedex

import com.example.android_pokedex.ApiPokemon
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
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        //recyclerView.adapter = PokemonAdapter(pokemons)
        //recyclerView.layoutManager = LinearLayoutManager(this)

        // Configuration du RecyclerView et de l'adapter
        GlobalScope.launch {
            val pokemonList = getPokemonList()
            withContext(Dispatchers.Main) {
                adapter = PokemonAdapter(pokemonList)
                recyclerView.adapter = adapter
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private suspend fun getPokemonList(): List<Pokemon> = coroutineScope {
        val numbers = mutableListOf(Pokemon("Pikachu", "pikachu_image", "Description de Pikachu"))

        for (i in 1..15) {
            val response = ApiPokemon().getRequest("https://pokeapi.co/api/v2/pokemon/$i")

            if (i == 1) {
                withContext(Dispatchers.Main) {
                    numbers[0] = Pokemon(response.name, response.name+"_image", "Description de ${response.name}")
                }
            } else {
                withContext(Dispatchers.Main) {
                    numbers.add(Pokemon(response.name, response.name+"_image", "Description de ${response.name}"))
                }
            }
        }
        numbers
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
}