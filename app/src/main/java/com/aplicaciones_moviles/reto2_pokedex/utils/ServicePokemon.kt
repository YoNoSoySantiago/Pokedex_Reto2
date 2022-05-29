package com.aplicaciones_moviles.reto2_pokedex.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServicePokemon {
    private val retrofit: Retrofit = Retrofit.Builder().baseUrl("https://pokeapi.co")
        .addConverterFactory(GsonConverterFactory.create()).build()

    val api: PokemonAPI = retrofit.create(PokemonAPI:: class.java)
}