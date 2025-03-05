package com.tevs.myapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_login, container, false)
        val activity = requireActivity()
        val navController = NavHostFragment.findNavController(this)

        val loginInput = root.findViewById<EditText>(R.id.loginInput)
        val passwordInput = root.findViewById<EditText>(R.id.passwordInput)
        val isAutoLoginInput = root.findViewById<CheckBox>(R.id.autoLogin)

        val loginButton = root.findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            val errors = mutableListOf<String>()

            val login = loginInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            val isAutoLogin = isAutoLoginInput.isChecked

            val storage = activity.getSharedPreferences("settings", Context.MODE_PRIVATE)
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
                    Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
                }
            } else {
                // Все проверки пройдены

                storage.edit().putBoolean("is_auto_login", isAutoLogin).apply()

                navController.navigate(R.id.firstFragment)
            }
        }
        return root
    }
}