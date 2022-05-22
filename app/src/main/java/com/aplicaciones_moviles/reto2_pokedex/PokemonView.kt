package com.aplicaciones_moviles.reto2_pokedex

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class PokemonView(itemView:View) : RecyclerView.ViewHolder(itemView) {

    var pokeName : TextView = itemView.findViewById(R.id.txt_poke_name_info)
    var capturedDate : TextView = itemView.findViewById(R.id.txt_poke_date_info)
    var avatar: ImageView =  itemView.findViewById(R.id.img_poke_avatar_info)

    var layout: ConstraintLayout = itemView.findViewById(R.id.layout_pokerow)

    init {
        //Logica para mostrar la info del pokemon clickado
        layout.setOnClickListener{
            Toast.makeText(itemView.context,pokeName.text,Toast.LENGTH_LONG).show()
        }
    }
}