package com.aplicaciones_moviles.reto2_pokedex

class User  {
    var username :String
    private var pokes : ArrayList<Pokemon>

    constructor(username:String){
        this.username = username
        this.pokes = ArrayList()
    }
}