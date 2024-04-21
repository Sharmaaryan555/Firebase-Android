package com.example.firebaseconnectivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var  register:Button;
     lateinit var login:Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        register = findViewById<Button>(R.id.register)
        login = findViewById<Button>(R.id.login)


        register.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java));
            finish()
        }
        login.setOnClickListener{
            startActivity(Intent(this,LoginActivity::class.java));
            finish();
        }
    }
}