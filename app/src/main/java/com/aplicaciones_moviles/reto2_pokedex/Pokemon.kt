package com.aplicaciones_moviles.reto2_pokedex

import java.io.Serializable
import java.util.*

class Pokemon : Serializable {

    //Profile Info
    var id: String
    var name: String
    var type: String
    var image_url:String
    var captured_date: Date

    //Stats
    var defense: Int
    var attack: Int
    var speed: Int
    var health: Int

    constructor(id:String, name:String, type:String, image_url:String, captured_date:Date, defense:Int, attack:Int, speed:Int, health:Int){
        this.id = id;
        this.name = name;
        this.type = type
        this.image_url = image_url
        this.captured_date = captured_date

        this.defense = defense
        this.attack = attack
        this.speed = speed
        this.health = health
    }

    //Contructor de prueba, se debe elimnar al terminar
    constructor(){
        this.id = "1";
        this.name = "DittoTest";
        this.type = "Normal"
        this.image_url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png"
        this.captured_date = Date()

        this.defense = 50
        this.attack = 50
        this.speed = 99
        this.health = 190
    }
}