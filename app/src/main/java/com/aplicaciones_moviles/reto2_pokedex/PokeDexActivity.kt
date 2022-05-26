package com.aplicaciones_moviles.reto2_pokedex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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

        //Logina para buscar y ver un nuevo pokemon
        binding.btnSearchNewPoke.setOnClickListener{
            //Dummy

            //revisar si existe
            var exist = true //Dummy
            if(exist){
                //Revisar si ya ha sido capturado
                var catched = false // dummy

                if(catched){
                    //buscarlo
                    var poke = Pokemon() //Dummy


                    val intent = Intent(this, ViewPokeActivity::class.java)
                    intent.putExtra("pokemon",poke)
                    intent.putExtra("newPoke",false)
                    startActivity(intent)
                }else{
                    val intent = Intent(this, ViewPokeActivity::class.java)
                    intent.putExtra("pokemon",Pokemon())
                    intent.putExtra("newPoke",true)
                    startActivity(intent)
                }
            }else{
                Toast.makeText(this,"Este pokemon no existe",Toast.LENGTH_SHORT)
            }
        }

        //Logica para buscar un pokemon ya existente
        binding.btnSearchMyPokes.setOnClickListener{

        }

    }
}