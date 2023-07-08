package com.moveis.new_fabw.view

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.moveis.new_fabw.R
import com.moveis.new_fabw.databinding.FragmentSignUpBinding

class SignUp : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var navigation: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        navigation = findNavController()

        binding.signupButton.setOnClickListener {
            val email = binding.signupEmail.text.toString()
            val password = binding.signupPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                showSnackbar(view, "Preencha todos os campos!", Color.RED)
            } else {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showSnackbar(view, "Usuário cadastrado com sucesso!", Color.GREEN)
                        clearFields()
                        navigation.navigate(R.id.action_signUp_to_login)
                    }
                }.addOnFailureListener { exception ->
                    showSnackbar(view, "Falha no cadastro: ${exception.message}", Color.RED)
                }
            }
        }
    }

    private fun showSnackbar(view: View, message: String, color: Int) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        snackbar.view.setBackgroundColor(color)
        snackbar.show()
    }

    private fun clearFields() {
        binding.signupEmail.setText("")
        binding.signupPassword.setText("")
    }
}