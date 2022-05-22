package com.aplicaciones_moviles.reto2_pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aplicaciones_moviles.reto2_pokedex.databinding.ActivityPokeDexBinding

class PokeDexActivity : AppCompatActivity() {

    private lateinit var layoutManager: LinearLayoutManager

    private lateinit var adapter:PokemonAdapter

    private lateinit var binding: ActivityPokeDexBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPokeDexBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Recycler config
        layoutManager = LinearLayoutManager(this)

        binding.pokeRecycler.layoutManager = layoutManager
        binding.pokeRecycler.setHasFixedSize(true)

        adapter = PokemonAdapter()
        binding.pokeRecycler.adapter = adapter

        //Logica para agregar un nuevo pokemon
        binding.btnCatchNewPoke.setOnClickListener{

        }
    }
}