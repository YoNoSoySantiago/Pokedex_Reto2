package com.aplicaciones_moviles.reto2_pokedex.recycle

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aplicaciones_moviles.reto2_pokedex.R
import com.aplicaciones_moviles.reto2_pokedex.model.Pokemon
import java.io.InputStream
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class PokemonAdapter : RecyclerView.Adapter<PokemonView>() {

    private var pokes = ArrayList<Pokemon>()
    var activityContext: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonView {

        var inflater = LayoutInflater.from(parent.context)
        val row = inflater.inflate(R.layout.poke_row,parent,false)

        return PokemonView(row)
    }

    override fun onBindViewHolder(holder: PokemonView, position: Int) {
        holder.pokeName.text = pokes[position].name
        val dateLong =  pokes[position].captured_date.toLong()
        val date = Date(dateLong)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        val datePo = format.format(date)
        holder.capturedDate.text = datePo

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

    fun addPokemon(poke: Pokemon){
        this.pokes.add(poke)
        notifyItemInserted(this.pokes.size-1)
    }

    fun deletePokemon(poke: Pokemon){
        pokes.remove(poke)
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

    fun clean() {
        pokes = ArrayList<Pokemon>()
        notifyDataSetChanged()
    }
}