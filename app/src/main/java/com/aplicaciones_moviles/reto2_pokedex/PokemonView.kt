package com.aplicaciones_moviles.reto2_pokedex

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class PokemonView(itemView:View) : RecyclerView.ViewHolder(itemView) {

    var pokeName : TextView = itemView.findViewById(R.id.txt_poke_name_info)
    var capturedDate : TextView = itemView.findViewById(R.id.txt_poke_date_info)
    var avatar: ImageView =  itemView.findViewById(R.id.img_poke_avatar_info)

    var layout: ConstraintLayout = itemView.findViewById(R.id.layout_pokerow)
    lateinit var poke: Pokemon

    init {
        //Logica para mostrar la info del pokemon clickado
        layout.setOnClickListener{
            Utils.startActivity(itemView.context,ViewPokeActivity::class.java,poke)
        }
    }

    class Utils {
        companion object {
            fun startActivity(context: Context, clazz: Class<*>,poke:Pokemon) {
                val intent = Intent(context, clazz)
                intent.putExtra("pokemon",poke)
                intent.putExtra("newPoke",false)
                context.startActivity(intent)
            }
        }
    }
}

