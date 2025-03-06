package com.tevs.myapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_register, container, false)
        val activity = requireActivity()
        val navController = NavHostFragment.findNavController(this)

        var activeTab = "tabEmail"
        val tabPhone = root.findViewById<TextView>(R.id.tabPhone);
        val tabEmail = root.findViewById<TextView>(R.id.tabEmail)

        val loginInput = root.findViewById<EditText>(R.id.emailInput)
        val passwordInput = root.findViewById<EditText>(R.id.passwordInput)
        val passwordRepeatInput = root.findViewById<EditText>(R.id.passwordRepeatInput)

        val registerButton = root.findViewById<Button>(R.id.registerButton)


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
                    Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
                }
            } else {
                // Все проверки пройдены

                val firebaseAuth = Firebase.auth
                val storage = activity.getSharedPreferences("settings", Context.MODE_PRIVATE)

                if (activeTab == "tabEmail") {
                    firebaseAuth.createUserWithEmailAndPassword(contact, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                storage.edit().putString("step", "login").apply()
                                navController.navigate(R.id.splashFragment)
                            } else {
                                Toast.makeText(
                                    context,
                                    "Ошибка регистрации: ${task.exception?.localizedMessage}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(
                                context,
                                "Ошибка регистрации: ${exception.localizedMessage}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                } else {
                    storage.edit().putString("step", "login").apply()
                    storage.edit().putString("login", contact).apply()
                    storage.edit().putString("password", password).apply()
                }

                navController.navigate(R.id.splashFragment)
            }
        }

        return root
    }
}