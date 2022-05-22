package com.aplicaciones_moviles.reto2_pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aplicaciones_moviles.reto2_pokedex.databinding.ActivityViewPokeBinding

private lateinit var binding: ActivityViewPokeBinding
class ViewPokeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPokeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}