package com.aplicaciones_moviles.reto2_pokedex

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.io.InputStream
import java.net.URL


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

        val thread = Thread {
            try {
                var draw = LoadImageFromWebOperations(pokes[position].image_url)
                holder.avatar.setImageDrawable(draw)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
        holder.poke = pokes[position]

    }

    override fun getItemCount(): Int {
        return pokes.size
    }

    fun LoadImageFromWebOperations(url: String?): Drawable? {
        return try {
            val `is`: InputStream = URL(url).getContent() as InputStream
            Drawable.createFromStream(`is`, "avatar")
        } catch (e: Exception) {
            Log.d("Exception: ",e.toString())
            null
        }
    }
}