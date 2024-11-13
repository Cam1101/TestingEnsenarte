package com.puj.proyectoensenarte

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.puj.proyectoensenarte.authentication.ForgotPasswordActivity
import com.puj.proyectoensenarte.databinding.ActivityIniciarSesionBinding

class IniciarSesionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIniciarSesionBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIniciarSesionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.textoCrearCuenta.setOnClickListener {
            val intent = Intent(this, CrearCuentaActivity::class.java)
            startActivity(intent)
        }

        binding.botonIniciarSesion.setOnClickListener {
            val email: String = binding.textFieldEmail.editText?.text.toString()
            val password: String = binding.textFieldPassword.editText?.text.toString()
            Log.d(TAG, "Email: $email")
            Log.d(TAG, "Password: $password")
            signIn(email, password)
        }

        binding.olvidasteContraText.setOnClickListener {
            // Redirect to forgetPassword screen
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    fun signIn(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            showMessage("Email and Password must not be empty")
            return
        }

        if (!isEmailValid(email)) {
            showMessage("Invalid email format")
            return
        }

        if (password.length < 6) {
            showMessage("Password must be at least 6 characters long")
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    val intent = Intent(this, BottomNavigationActivity::class.java)
                    startActivity(intent)
                } else {
                    val exception = task.exception
                    when (exception) {
                        is FirebaseAuthInvalidUserException -> {
                            showMessage("No account found with this email.")
                        }
                        is FirebaseAuthInvalidCredentialsException -> {
                            showMessage("Incorrect password.")
                        }
                        is FirebaseNetworkException -> {
                            showMessage("Network error. Please try again later.")
                        }
                        else -> {
                            showMessage("Authentication failed: ${exception?.message}")
                        }
                    }
                }
            }
    }

    private fun showMessage(message: String) {
        binding.textViewMessage.text = message
        binding.textViewMessage.visibility = android.view.View.VISIBLE
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
