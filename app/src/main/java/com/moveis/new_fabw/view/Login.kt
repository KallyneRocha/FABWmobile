package com.moveis.new_fabw.view

import android.graphics.Color
//import android.os.Build.VERSION_CODES.R
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
import com.moveis.new_fabw.databinding.FragmentLoginBinding

class Login : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var navigation: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        navigation = findNavController()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        binding.loginButton.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                showSnackbar(view, "Preencha todos os campos!", Color.RED)
            } else {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showSnackbar(view, "Login bem-sucedido!", Color.GREEN)
                        clearFields()
                        navigation.navigate(R.id.action_login_to_navFragment)
                    } else {
                        showSnackbar(view, "Falha no login. Verifique suas credenciais!", Color.RED)
                    }
                }
            }
        }

        binding.signupRedirectText.setOnClickListener {
            // Navegar para a tela de cadastro (SignUpFragment)
            /*val signUpFragment = SignUp()
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainerView, signUpFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()*/
            navigation.navigate(R.id.action_login_to_signUp)
        }
    }

    private fun showSnackbar(view: View, message: String, color: Int) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        snackbar.view.setBackgroundColor(color)
        snackbar.show()
    }

    private fun clearFields() {
        binding.loginEmail.setText("")
        binding.loginPassword.setText("")
    }
}
