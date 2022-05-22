package com.aplicaciones_moviles.reto2_pokedex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aplicaciones_moviles.reto2_pokedex.databinding.ActivityMainBinding
import com.aplicaciones_moviles.reto2_pokedex.databinding.ActivityPokeDexBinding


class PokemonAdapter : RecyclerView.Adapter<PokemonView>() {

    private val pokes = ArrayList<Pokemon>()

    init {
        pokes.add(Pokemon())
        pokes.add(Pokemon())
        pokes.add(Pokemon())
        pokes.add(Pokemon())
        pokes.add(Pokemon())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonView {

        var inflater = LayoutInflater.from(parent.context)
        val row = inflater.inflate(R.layout.poke_row,parent,false)

        return PokemonView(row)
    }

    override fun onBindViewHolder(holder: PokemonView, position: Int) {
        holder.pokeName.text = pokes[position].name
        holder.capturedDate.text = pokes[position].captured_date.toString()

    }

    override fun getItemCount(): Int {
        return pokes.size
    }
}