package com.aplicaciones_moviles.reto2_pokedex.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aplicaciones_moviles.reto2_pokedex.databinding.ActivityMainBinding
import com.aplicaciones_moviles.reto2_pokedex.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*


const val POKE_API_URL = "https://pokemonapp-c6a9f-default-rtdb.firebaseio.com"
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        binding.btnLogin.setOnClickListener(::login)
    }

    private fun login(view : View){

        val username = binding.editTextUsername.text.toString()
        var user = User(UUID.randomUUID().toString(), username)

        val query = Firebase.firestore.collection("users").whereEqualTo("username", username)

        query.get().addOnCompleteListener { task ->

            if(task.result?.size() == 0){
                Firebase.firestore.collection("users").document(user.id).set(user)
            }
            else{
                for(document in task.result!!){
                    user = document.toObject(User::class.java)
                    break
                }
            }
        }

        val intent = Intent(this, PokeDexActivity::class.java).apply {
            putExtra("username", user.username)
        }
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        // super.onBackPressed();
        Toast.makeText(this, "accion no permitida", Toast.LENGTH_SHORT).show()
        return
    }
}


