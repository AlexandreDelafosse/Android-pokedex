package com.example.android_pokedex

// PokemonDetailActivity.kt
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView

class PokemonDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detail)

        val pokemon = intent.getParcelableExtra<Pokemon>("pokemon")

        val nameTextView = findViewById<TextView>(R.id.nameTextView)
        val imageView = findViewById<ImageView>(R.id.imageView)
        val descriptionTextView = findViewById<TextView>(R.id.descriptionTextView)

        nameTextView.text = pokemon?.name?.capitalize()

        imageView.setImageResource(resources.getIdentifier(pokemon?.image, "drawable", packageName))

        descriptionTextView.text = pokemon?.description
    }
}
