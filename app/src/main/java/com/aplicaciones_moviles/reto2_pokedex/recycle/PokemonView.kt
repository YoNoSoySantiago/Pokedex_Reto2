package com.aplicaciones_moviles.reto2_pokedex.recycle

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.aplicaciones_moviles.reto2_pokedex.R
import com.aplicaciones_moviles.reto2_pokedex.activity.ViewPokeActivity
import com.aplicaciones_moviles.reto2_pokedex.model.Pokemon

class PokemonView(itemView:View) : RecyclerView.ViewHolder(itemView) {

    var pokeName : TextView = itemView.findViewById(R.id.txt_poke_name_info)
    var capturedDate : TextView = itemView.findViewById(R.id.txt_poke_date_info)
    var avatar: ImageView =  itemView.findViewById(R.id.img_poke_avatar_info)

    var layout: ConstraintLayout = itemView.findViewById(R.id.layout_pokerow)
    lateinit var poke: Pokemon

    init {
        //Logica para mostrar la info del pokemon clickado
        layout.setOnClickListener{
            Utils.startActivity(itemView.context, ViewPokeActivity::class.java, poke)
        }
    }

    class Utils {
        companion object {
            fun startActivity(context: Context, clazz: Class<*>,poke: Pokemon) {
                val intent = Intent(context, clazz)
                intent.putExtra("pokemon",poke)
                intent.putExtra("newPoke",false)
                intent.putExtra("imgUrl",poke!!.image_url)
                intent.putExtra("name", poke!!.name)
                intent.putExtra("username_trainer", poke!!.username_trainer)
                intent.putExtra("type", poke!!.type)

                intent.putExtra("id", poke!!.id)

                intent.putExtra("defense", poke!!.defense)
                intent.putExtra("attack", poke!!.attack)
                intent.putExtra("speed", poke!!.speed)
                intent.putExtra("health", poke!!.health)
                context.startActivity(intent)
            }
        }
    }
}

