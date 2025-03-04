package com.tevs.myapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        var activeTab = "tabEmail"
        val tabPhone = findViewById<TextView>(R.id.tabPhone);
        val tabEmail = findViewById<TextView>(R.id.tabEmail)

        val loginInput = findViewById<EditText>(R.id.emailInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val passwordRepeatInput = findViewById<EditText>(R.id.passwordRepeatInput)

        val registerButton = findViewById<Button>(R.id.registerButton)


        tabEmail.setOnClickListener {
            if (activeTab != "tabEmail") {
                activeTab = "tabEmail"

                tabEmail.setTextColor(Color.parseColor("#6d5a7f"))
                tabEmail.setTypeface(Typeface.DEFAULT_BOLD)

                tabPhone.setTextColor(Color.parseColor("#000000"))
                tabPhone.setTypeface(Typeface.DEFAULT)

                loginInput.text = null
                loginInput.hint = getString(R.string.email)
                loginInput.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)

            }
        }

        tabPhone.setOnClickListener {
            if (activeTab != "tabPhone") {
                activeTab = "tabPhone"

                tabEmail.setTextColor(Color.parseColor("#000000"))
                tabEmail.setTypeface(Typeface.DEFAULT)

                tabPhone.setTextColor(Color.parseColor("#6d5a7f"))
                tabPhone.setTypeface(Typeface.DEFAULT_BOLD)

                loginInput.text = null
                loginInput.hint = getString(R.string.reg_phone)
                loginInput.setInputType(InputType.TYPE_CLASS_PHONE)
            }
        }

        registerButton.setOnClickListener {
            val errors = mutableListOf<String>()

            val contact = loginInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            val passwordRepeat = passwordRepeatInput.text.toString().trim()

            if (activeTab == "tabEmail") {
                if (!contact.contains("@")) {
                    errors.add("Введите правильный email")
                }
            } else if (activeTab == "tabPhone") {
                if (!contact.startsWith("+")) {
                    errors.add("Введите правильный телефон")
                }
            }

            if (password.isEmpty()) {
                errors.add("Введите пароль")
            } else if (password.length < 8) {
                errors.add("Минимальная длина пароля 8 символов")
            }

            if (passwordRepeat.isEmpty()) {
                errors.add("Подтвердите пароль")
            }

            if (password.isNotEmpty() && passwordRepeat.isNotEmpty() && password != passwordRepeat) {
                errors.add("Пароли должны совпадать")
            }

            if (errors.isNotEmpty()) {
                for (error in errors) {
                    Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                }
            } else {
                // Все проверки пройдены

                val storage = getSharedPreferences("settings", Context.MODE_PRIVATE)
                storage.edit().putString("login", contact).apply()
                storage.edit().putString("password", password).apply()
                storage.edit().putBoolean("is_auto_login", false).apply()

                val intent = Intent(this, SplashActivity::class.java)
                startActivity(intent)
            }
        }
    }
}