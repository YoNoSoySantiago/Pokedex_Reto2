package com.aplicaciones_moviles.reto2_pokedex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aplicaciones_moviles.reto2_pokedex.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        binding.btnLogin.setOnClickListener{
            val intent = Intent(this, PokeDexActivity::class.java)
            startActivity(intent)
        }
    }
}