package com.aplicaciones_moviles.reto2_pokedex.activity

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.aplicaciones_moviles.reto2_pokedex.databinding.ActivityViewPokeBinding
import com.aplicaciones_moviles.reto2_pokedex.model.Pokemon
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.InputStream
import java.net.URL

private lateinit var binding: ActivityViewPokeBinding


class ViewPokeActivity : AppCompatActivity() {

    lateinit var poke: Pokemon
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPokeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val new = intent.extras?.getBoolean("newPoke")
        val pokemon = intent.extras?.getSerializable("pokemon") as? Pokemon
        if(new!!){
            binding.btnCatchPoke.visibility = View.VISIBLE
            binding.btnFreePoke.visibility = View.INVISIBLE
        }else{
            binding.btnCatchPoke.visibility = View.INVISIBLE
            binding.btnFreePoke.visibility = View.VISIBLE
        }
        binding.txtPokeName.text = intent.extras?.getString("name")
        binding.txtPokeType.text = intent.extras?.getString("type")
        val image=intent.extras?.getString("imgUrl")
        binding.txtAtaque.text = intent.extras?.getString("attack")
        binding.txtDefensa.text = intent.extras?.getString("defense")
        binding.txtVelocidad.text = intent.extras?.getString("speed")
        binding.txtVida.text =intent.extras?.getString("health")

        val thread = Thread {
            try {
                var draw = LoadImageFromWebOperations(image)
                binding.imgAvatarInfo.setImageDrawable(draw)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
        thread.start()

        binding.btnCatchPoke.setOnClickListener{
            val intent = Intent(this, PokeDexActivity::class.java).apply {
                putExtra("pokemon", pokemon)
                putExtra("username", intent.extras?.getString("username_trainer"))
            }
            startActivity(intent)
            finish()
        }
        binding.btnFreePoke.setOnClickListener{
            val idPoke = intent.extras?.getString("id")

            if (idPoke != null) {
                Firebase.firestore.collection("pokemons").document(idPoke)
                    .delete()
                    .addOnSuccessListener {
                        Log.d(
                            ">>> ",
                            "DocumentSnapshot successfully deleted!"
                        )
                    }
                    .addOnFailureListener { e -> Log.w(">>> ", "Error deleting document", e) }

                val intent = Intent(this, PokeDexActivity::class.java).apply {
                    putExtra("username", intent.extras?.getString("username_trainer"))
                }
                startActivity(intent)
                finish()
            }
        }

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
