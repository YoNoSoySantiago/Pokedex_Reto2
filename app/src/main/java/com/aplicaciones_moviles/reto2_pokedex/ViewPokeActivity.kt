package com.aplicaciones_moviles.reto2_pokedex

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.transition.Visibility
import com.aplicaciones_moviles.reto2_pokedex.databinding.ActivityViewPokeBinding
import java.io.InputStream
import java.net.URL

private lateinit var binding: ActivityViewPokeBinding


class ViewPokeActivity : AppCompatActivity() {

    lateinit var poke:Pokemon
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPokeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //geting pokemon
        this.poke = intent.extras?.getSerializable("pokemon") as Pokemon
        val new = intent.extras?.getBoolean("newPoke")
        if(new!!){
            binding.btnCatchPoke.visibility = View.VISIBLE
            binding.btnFreePoke.visibility = View.INVISIBLE
        }else{
            binding.btnCatchPoke.visibility = View.INVISIBLE
            binding.btnFreePoke.visibility = View.VISIBLE
        }
        //Setting
        val thread = Thread {
            try {
                var draw = LoadImageFromWebOperations(poke.image_url)
                binding.imgAvatarInfo.setImageDrawable(draw)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
        thread.start()

        binding.txtPokeName.text = poke.name
        binding.txtPokeType.text = poke.type

        binding.txtAtaque.text = poke.attack.toString()
        binding.txtDefensa.text = poke.defense.toString()
        binding.txtVelocidad.text = poke.speed.toString()
        binding.txtVida.text = poke.health.toString()

        val launcher = registerForActivityResult(StartActivityForResult(),::onDeletePoke)
    }

    private fun onDeletePoke(result: ActivityResult) {

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