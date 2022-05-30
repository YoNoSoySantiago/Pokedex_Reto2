package com.aplicaciones_moviles.reto2_pokedex.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aplicaciones_moviles.reto2_pokedex.databinding.ActivityPokeDexBinding
import com.aplicaciones_moviles.reto2_pokedex.model.Pokemon
import com.aplicaciones_moviles.reto2_pokedex.recycle.PokemonAdapter
import com.aplicaciones_moviles.reto2_pokedex.utils.Constants
import com.aplicaciones_moviles.reto2_pokedex.utils.HttpsWeb
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.*

class PokeDexActivity : AppCompatActivity() {

    private lateinit var layoutManager: LinearLayoutManager

    private lateinit var adapter: PokemonAdapter

    private lateinit var binding: ActivityPokeDexBinding

    private var username:String? = null

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

        username= intent.extras?.getString("username")

        binding.btnLogout.setOnClickListener {
            finish()
            startActivity(Intent(this,MainActivity::class.java))
        }
        //Logica para agregar un nuevo pokemon al usuario
        binding.btnCatchNewPoke.setOnClickListener{
            val pokemonName = binding.editTextSarchNewPoke.text.toString().lowercase()
            val query = Firebase.firestore.collection("pokemons").whereEqualTo("username_trainer", username)
            query.get().addOnCompleteListener { task ->
                var alreadyExist = false
                if (task.result?.size() != 0) {
                    for(document in task.result!!){
                        val poke = document.toObject(Pokemon::class.java)
                        if(poke.name == pokemonName){
                            alreadyExist = true
                            break
                        }
                    }
                    if(alreadyExist){
                        Toast.makeText(this,"El pokemon ya fue atrapado",Toast.LENGTH_LONG).show()
                    }
                }
                if(!alreadyExist){
                    getPokemonByName(pokemonName)
                }
            }

        }
        //Logina para buscar y ver un nuevo pokemon
        binding.btnSearchNewPoke.setOnClickListener{
            var find=false
            val pokename = binding.editTextSarchNewPoke.text.toString().lowercase()
            val query = Firebase.firestore.collection("pokemons").whereEqualTo("username_trainer", username)
            query.get().addOnCompleteListener { task ->
                if (task.result?.size() != 0) {
                    for (document in task.result!!) {
                        Log.e("Document",document.toString())
                        val poke = document.toObject(Pokemon::class.java)
                        if(poke.name == pokename){
                            find=true
                            val intentt = Intent(this, ViewPokeActivity::class.java).apply {
                                putExtra("newPoke", false)
                                putExtra("imgUrl", poke.image_url)
                                putExtra("name", poke.name)
                                putExtra("username_trainer", poke.username_trainer)
                                putExtra("type", poke.type)
                                putExtra("id", poke.id)

                                putExtra("defense", poke.defense)
                                putExtra("attack", poke.attack)
                                putExtra("speed", poke.speed)
                                putExtra("health", poke.health)
                            }
                            finish()
                            startActivity(intent)
                        }
                    }
                }
            }
            if(!find){
                findPokemon(pokename)
            }
        }

        //Logica para buscar un pokemon ya existente
        binding.btnSearchMyPokes.setOnClickListener{
            val pokename = binding.editTextSeachMyPoke.text.toString().lowercase()
            getPokemonsFromUserByPokename(pokename)
        }

    }

    private fun findPokemon(pokename:String){
        lifecycleScope.launch(Dispatchers.IO){
            try{
                val response = HttpsWeb().GETRequest("${Constants.POKE_API_URL}/pokemon/${pokename}")
                val jsonPokemon = JSONObject(response)

                //Name
                val name = jsonPokemon.getString("name")

                //image_url
                val sprites = jsonPokemon.getJSONObject("sprites")
                val imageUrl = sprites.getString("front_default")

                //stats
                val stats = jsonPokemon.getJSONArray("stats")

                //speed
                val jsonSpeed = stats.getJSONObject(5)
                val speed = jsonSpeed.getString("base_stat")

                //health
                val jsonHP = stats.getJSONObject(0)
                val health = jsonHP.getString("base_stat")

                //defense
                val jsonDefense = stats.getJSONObject(2)
                val defense = jsonDefense.getString("base_stat")

                //attack
                val jsonAttack = stats.getJSONObject(1)
                val attack = jsonAttack.getString("base_stat")

                // types
                val jsonTypes = jsonPokemon.getJSONArray("types")
                var type = ""

                var count = 0
                while(count<jsonTypes.length()){
                    val jsonType = jsonTypes.getJSONObject(count)
                    val rawType = jsonType.getJSONObject("type")
                    type = type + rawType.getString("name") + ", "
                    count++
                }

                val newPokemon = username?.let { Pokemon(it,UUID.randomUUID().toString(),name,type,imageUrl,"${System.currentTimeMillis()}",defense,attack,speed,health) }
                if(newPokemon!=null) {
                    val intent = Intent(
                        applicationContext,
                        ViewPokeActivity::class.java
                    ).apply {
                        putExtra("pokemon", newPokemon)
                        putExtra("newPoke", true)
                        putExtra("imgUrl", newPokemon.image_url)
                        putExtra("name", newPokemon.name)
                        putExtra("username_trainer", newPokemon.username_trainer)
                        putExtra("type", newPokemon.type)

                        putExtra("id", newPokemon.id)

                        putExtra("defense", newPokemon.defense)
                        putExtra("attack", newPokemon.attack)
                        putExtra("speed", newPokemon.speed)
                        putExtra("health", newPokemon.health)
                    }
                    finish()
                    startActivity(intent)
                }else{
                    Toast.makeText(applicationContext,"Este pokemon no existe en la API",Toast.LENGTH_LONG).show()
                }
            }catch (e: Exception){

            }
        }
    }

    private fun getPokemonsFromUserByPokename(pokename: String){
        lifecycleScope.launch(Dispatchers.IO){
            withContext(Dispatchers.Main){
                adapter.clean()
            }
        }
        val query = Firebase.firestore.collection("pokemons").whereEqualTo("username_trainer", username).orderBy("captured_date", Query.Direction.ASCENDING)
        query.get().addOnCompleteListener { task ->
            if(task.result?.size() != 0){
                for(document in task.result!!){
                    val poke = document.toObject(Pokemon::class.java)
                    lifecycleScope.launch(Dispatchers.IO){
                        withContext(Dispatchers.Main){
                            if(poke.name.startsWith(pokename.lowercase())){
                                adapter.addPokemon(poke)
                            }
                        }
                    }
                }
            }else{
                Toast.makeText(this,"Actualmente no tienes pokemones",Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun getPokemonsfromUser(){
        lifecycleScope.launch(Dispatchers.IO){
            withContext(Dispatchers.Main){
                adapter.clean()
            }
        }
        val query = Firebase.firestore.collection("pokemons").whereEqualTo("username_trainer", username).orderBy("captured_date", Query.Direction.ASCENDING)
        query.get().addOnCompleteListener { task ->
            if(task.result?.size() != 0){
                for(document in task.result!!){
                    val poke = document.toObject(Pokemon::class.java)
                    lifecycleScope.launch(Dispatchers.IO){
                        withContext(Dispatchers.Main){
                            adapter.addPokemon(poke)
                        }
                    }
                }
            }
        }
    }
    private fun getPokemonByName(name : String){
        lifecycleScope.launch(Dispatchers.IO){
            try{
                val response = HttpsWeb().GETRequest("${Constants.POKE_API_URL}/pokemon/${name}")
                val jsonPokemon = JSONObject(response)
                //Name
                val name = jsonPokemon.getString("name")

                //image_url
                val sprites = jsonPokemon.getJSONObject("sprites")
                val imageUrl = sprites.getString("front_default")

                //stats
                val stats = jsonPokemon.getJSONArray("stats")

                //speed
                val jsonSpeed = stats.getJSONObject(5)
                val speed = jsonSpeed.getString("base_stat")

                //health
                val jsonHP = stats.getJSONObject(0)
                val health = jsonHP.getString("base_stat")

                //defense
                val jsonDefense = stats.getJSONObject(2)
                val defense = jsonDefense.getString("base_stat")

                //attack
                val jsonAttack = stats.getJSONObject(1)
                val attack = jsonAttack.getString("base_stat")

                // types
                val jsonTypes = jsonPokemon.getJSONArray("types")
                var type = ""

                var count = 0
                while(count<jsonTypes.length()){
                    val jsonType = jsonTypes.getJSONObject(count)
                    val rawType = jsonType.getJSONObject("type")
                    type = type + rawType.getString("name") + ", "
                    count++
                }
                val newPokemon = username?.let { Pokemon(it,UUID.randomUUID().toString(),name,type,imageUrl,"${System.currentTimeMillis()}",defense,attack,speed,health) }

                if (newPokemon != null) {
                    Firebase.firestore.collection("pokemons").document(newPokemon.id).set(newPokemon)
                    getPokemonsfromUser()
                }else{
                    Toast.makeText(applicationContext,"Este pokemon no existe",Toast.LENGTH_SHORT).show()
                }
            }catch (e: Exception){

            }
        }

    }
    override fun onStart() {
        val pokemonCatch= intent.extras?.getSerializable("pokemon") as? Pokemon

        if(pokemonCatch!=null){
            getPokemonByName(pokemonCatch.name)
        }else{
            getPokemonsfromUser()
        }
        super.onStart()
    }
    override fun onBackPressed() {
        // super.onBackPressed();
        Toast.makeText(this, "accion no permitida", Toast.LENGTH_SHORT).show()
        return
    }
}