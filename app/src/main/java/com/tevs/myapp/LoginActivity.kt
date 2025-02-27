package com.tevs.myapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val loginInput = findViewById<EditText>(R.id.loginInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val isAutoLoginInput = findViewById<CheckBox>(R.id.autoLogin)

        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            val errors = mutableListOf<String>()

            val login = loginInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            val isAutoLogin = isAutoLoginInput.isChecked

            val storage = getSharedPreferences("settings", Context.MODE_PRIVATE)
            val loginExist = storage.getString("login", "")
            val passwordExist = storage.getString("password", "")

            if (!login.contains("@") && !login.startsWith("+")) {
                errors.add("Введите правильный email или телефон")
            }

            if (password.isEmpty()) {
                errors.add("Введите пароль")
            } else if (password.length < 8) {
                errors.add("Минимальная длина пароля 8 символов")
            }

            if (login != loginExist || password != passwordExist) {
                errors.add("Неверный логин или пароль")
            }

            if (errors.isNotEmpty()) {
                for (error in errors) {
                    Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                }
            } else {
                // Все проверки пройдены

                storage.edit().putBoolean("is_auto_login", isAutoLogin).apply()

                val intent = Intent(this, ContentActivity::class.java)
                startActivity(intent)
            }
        }
    }

}