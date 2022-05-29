package com.aplicaciones_moviles.reto2_pokedex.utils

import com.aplicaciones_moviles.reto2_pokedex.model.Pokemon
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

public interface PokemonAPI {
    @GET("https://pokeapi.co/api/v2/pokemon/{name}")
    suspend fun getPokemonByName(@Path("name")name: String) : Response<Pokemon>
}