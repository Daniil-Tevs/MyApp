package com.tevs.myapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment

class SplashFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_splash, container, false)

        val navController = NavHostFragment.findNavController(this)
        val storage = requireActivity().getSharedPreferences("settings", Context.MODE_PRIVATE)

        val step = storage.getString("step", "")
        val isAutoLogin = storage.getBoolean("is_auto_login", false)

        if (step.isNullOrEmpty())
            navController.navigate(R.id.registerFragment)
        else if (step == "login")
            navController.navigate(R.id.loginFragment)
        else if (step == "auth")
            if (isAutoLogin)
                navController.navigate(R.id.firstFragment)
            else
                navController.navigate(R.id.loginFragment)

        return root
    }

}