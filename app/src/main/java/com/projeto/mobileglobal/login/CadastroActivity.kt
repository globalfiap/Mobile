package com.projeto.mobileglobal.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.projeto.mobileglobal.databinding.ActivityCadastroBinding
import com.projeto.mobileglobal.model.User

class CadastroActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private var binding: ActivityCadastroBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(LayoutInflater.from(this))
        setContentView(binding?.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference // Inicializa o Realtime Database

        binding?.botaoProximo?.setOnClickListener {
            val email = binding?.emailText?.text.toString()
            val password = binding?.senhaText?.text.toString()
            val confirmPassword = binding?.confirmarSenhaText?.text.toString()
            val nomeUsuario = binding?.nomeText?.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && nomeUsuario.isNotEmpty()) {
                if (password == confirmPassword) {
                    createUserWithEmailAndPassword(email, password, nomeUsuario)
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

    private fun createUserWithEmailAndPassword(email: String, password: String, nomeUsuario: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmailAndPassword: Success")
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        saveUserToDatabase(userId, nomeUsuario, email)
                    }

                    Toast.makeText(baseContext, "Usuário criado com sucesso!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, CadastroVeiculoActivity::class.java)
                    intent.putExtra("nomeUsuario", nomeUsuario)
                    startActivity(intent)
                    finish()
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

    private fun saveUserToDatabase(userId: String, nomeUsuario: String, email: String) {
        val user = User(nomeUsuario, email)
        database.child("users").child(userId).setValue(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "saveUserToDatabase: User data saved successfully")
                } else {
                    Log.w(TAG, "saveUserToDatabase: Failed to save user data", task.exception)
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
