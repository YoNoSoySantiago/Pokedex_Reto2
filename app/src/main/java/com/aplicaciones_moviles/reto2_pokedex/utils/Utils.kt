package com.aplicaciones_moviles.reto2_pokedex.utils

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception

fun request(coroutineScope: CoroutineScope,call:suspend()->Unit){
    coroutineScope.launch {
        try {
            call()
        }catch (exce:Exception){
            Log.e("API Error:","->${exce}")
        }
    }
}