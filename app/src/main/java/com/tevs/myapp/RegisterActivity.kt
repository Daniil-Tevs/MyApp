package com.tevs.myapp

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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

        val emailInput = findViewById<EditText>(R.id.emailInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val passwordRepeatInput = findViewById<EditText>(R.id.passwordRepeatInput)

        val errorBlock = findViewById<TextView>(R.id.errorBlock)
        val registerButton = findViewById<Button>(R.id.registerButton)


        tabEmail.setOnClickListener {
            if (activeTab != "tabEmail") {
                activeTab = "tabEmail"

                tabEmail.setTextColor(Color.parseColor("#6d5a7f"))
                tabEmail.setTypeface(Typeface.DEFAULT_BOLD)

                tabPhone.setTextColor(Color.parseColor("#000000"))
                tabPhone.setTypeface(Typeface.DEFAULT)

                emailInput.text = null
                emailInput.hint = getString(R.string.email)


                errorBlock.text = "";
                errorBlock.visibility = View.GONE
            }
        }

        tabPhone.setOnClickListener {
            if (activeTab != "tabPhone") {
                activeTab = "tabPhone"

                tabEmail.setTextColor(Color.parseColor("#000000"))
                tabEmail.setTypeface(Typeface.DEFAULT)

                tabPhone.setTextColor(Color.parseColor("#6d5a7f"))
                tabPhone.setTypeface(Typeface.DEFAULT_BOLD)

                emailInput.text = null
                emailInput.hint = getString(R.string.reg_phone)


                errorBlock.text = "";
                errorBlock.visibility = View.GONE
            }
        }

        registerButton.setOnClickListener {
            var errorText = ""

            val contact = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            val passwordRepeat = passwordRepeatInput.text.toString().trim()

            if (activeTab == "tabEmail") {
                if (!contact.contains("@"))
                    errorText += "Введите правильный email\n"
            } else if (activeTab == "tabPhone") {
                if (!contact.startsWith("+"))
                    errorText += "Введите правильный телефон\n"
            }

            if (password.isEmpty())
                errorText += "Введите пароль\n"
            else if (password.length < 8)
                errorText += "Минимальная длина пароля 8 символов\n"

            if (passwordRepeat.isEmpty()) {
                errorText += "Подтвердите пароль\n"
            }

            if (password.isNotEmpty() && passwordRepeat.isNotEmpty() && password != passwordRepeat) {
                errorText += "Пароли должны совпадать\n"
            }

            if (errorText != "") {
                errorBlock.text = errorText;
                errorBlock.visibility = View.VISIBLE
            } else {
                errorBlock.text = "";
                errorBlock.visibility = View.GONE
            }
        }
    }
}