package com.tevs.myapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val storage = getSharedPreferences("settings", Context.MODE_PRIVATE)

        val login = storage.getString("login", "")
        val password = storage.getString("password", "")
        val isAutoLogin = storage.getBoolean("is_auto_login", false)

        if (login == "" || password == "") {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        if (login != "" && password != "") {
            if (!isAutoLogin) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, ContentActivity::class.java)
                startActivity(intent)
            }
        }

    }
}