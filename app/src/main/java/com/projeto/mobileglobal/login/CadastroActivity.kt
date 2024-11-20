package com.projeto.mobileglobal.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.projeto.mobileglobal.databinding.ActivityCadastroBinding

class CadastroActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var binding: ActivityCadastroBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(LayoutInflater.from(this))
        setContentView(binding?.root)

        auth = FirebaseAuth.getInstance()

        binding?.botaoProximo?.setOnClickListener {
            val email = binding?.emailText?.text.toString()
            val password = binding?.senhaText?.text.toString()
            val confirmPassword = binding?.confirmarSenhaText?.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    createUserWithEmailAndPassword(email, password)
                } else {
                    Toast.makeText(
                        this@CadastroActivity,
                        "As senhas não coincidem, verifique!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    this@CadastroActivity,
                    "Por favor, preencha todos os campos.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun createUserWithEmailAndPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmailAndPassword: Success")
                    Toast.makeText(baseContext, "Usuário criado com sucesso!", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Log.w(TAG, "createUserWithEmailAndPassword: Failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Falha no cadastro: ${task.exception?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    companion object {
        private const val TAG = "CadastroActivity"
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
