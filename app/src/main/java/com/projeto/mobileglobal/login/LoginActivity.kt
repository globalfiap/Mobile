package com.projeto.mobileglobal.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.projeto.mobileglobal.databinding.ActivityLoginBinding
import com.projeto.mobileglobal.menu.HomeActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var binding: ActivityLoginBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(LayoutInflater.from(this))
        setContentView(binding?.root)

        auth = FirebaseAuth.getInstance()

        binding?.buttonAcessar?.setOnClickListener {
            val email = binding?.logintext?.text.toString()
            val password = binding?.senhatext?.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                signInEmailAndPassword(email, password)
            } else {
                Toast.makeText(
                    this@LoginActivity,
                    "Por favor, preencha os campos necessários.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding?.primeiroAcesso?.setOnClickListener {
            val intent = Intent(this@LoginActivity, CadastroActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signInEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInUserWithEmailAndPassword: Success")
                    Toast.makeText(baseContext, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()

                    // Redirecionar para HomeActivity
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    Log.w(TAG, "signInUserWithEmailAndPassword: Failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Falha no login: ${task.exception?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    companion object {
        private const val TAG = "EmailAndPassword"
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
