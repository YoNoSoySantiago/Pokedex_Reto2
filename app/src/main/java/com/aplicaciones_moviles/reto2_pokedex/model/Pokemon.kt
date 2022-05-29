package com.aplicaciones_moviles.reto2_pokedex.model

import java.io.Serializable
import java.util.*

data class Pokemon (

    //Profile Info
    var username_trainer: String="",
    var id: String="",
    var name: String="",
    var type: String="",
    var image_url:String="",
    var captured_date: String="",

    //Stats
    var defense: String="",
    var attack: String="",
    var speed: String="",
    var health: String=""
): Serializable
