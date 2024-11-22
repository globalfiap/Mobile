package com.projeto.mobileglobal.menu

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.projeto.mobileglobal.R
import com.projeto.mobileglobal.login.LoginActivity

class PerfilActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        // Inicializando Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference // Realtime Database

        // Recuperar dados do Intent
        val modelo = intent.getStringExtra("modelo")
        val imagemResId = intent.getIntExtra("imagem", R.drawable.sem_imagem)
        var nomeUsuario = intent.getStringExtra("nomeUsuario") ?: "Usuário"
        val marca = intent.getStringExtra("marca")
        val plug = intent.getStringExtra("plug")
        val placa = intent.getStringExtra("placa")

        // Vincular componentes da interface
        val textoNome = findViewById<TextView>(R.id.texto_nome)
        val textoModelo = findViewById<TextView>(R.id.texto_modelo)
        val textoMarca = findViewById<TextView>(R.id.texto_marca)
        val textoPlug = findViewById<TextView>(R.id.texto_plug)
        val textoPlaca = findViewById<TextView>(R.id.texto_placa)
        val editNome = findViewById<EditText>(R.id.edit_nome)
        val btnEditar = findViewById<Button>(R.id.btn_editar)
        val btnSalvar = findViewById<Button>(R.id.btn_salvar)
        val setaVoltar = findViewById<ImageView>(R.id.imageView4)
        val menuIcon = findViewById<ImageView>(R.id.menu)

        // Exibir os dados inicialmente usando strings formatadas
        textoNome.text = getString(R.string.nome_usuario, nomeUsuario)
        textoModelo.text = getString(R.string.modelo_veiculo, modelo)
        textoMarca.text = getString(R.string.marca_veiculo, marca)
        textoPlug.text = getString(R.string.plug_veiculo, plug)
        textoPlaca.text = getString(R.string.placa_veiculo, placa)

        // Lógica do botão de editar
        btnEditar.setOnClickListener {
            textoNome.visibility = View.GONE
            editNome.visibility = View.VISIBLE
            btnSalvar.visibility = View.VISIBLE
            btnEditar.visibility = View.GONE
            editNome.setText(nomeUsuario)
        }

        // Lógica do botão de salvar
        btnSalvar.setOnClickListener {
            nomeUsuario = editNome.text.toString()
            textoNome.text = getString(R.string.nome_usuario, nomeUsuario)
            textoNome.visibility = View.VISIBLE
            editNome.visibility = View.GONE
            btnSalvar.visibility = View.GONE
            btnEditar.visibility = View.VISIBLE

            // Atualiza o nome do usuário no Firebase
            val userId = auth.currentUser?.uid
            if (userId != null) {
                updateUserNameInDatabase(userId, nomeUsuario)
            }
        }

        // Voltar
        setaVoltar.setOnClickListener {
            finish()
        }

        // PopMenu
        menuIcon.setOnClickListener {
            showPopupMenu(it)
        }
    }


    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        menuInflater.inflate(R.menu.menu_profile, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.action_logout -> {

                    auth.signOut()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    // Função para atualizar o nome do usuário no Firebase
    private fun updateUserNameInDatabase(userId: String, nomeUsuario: String) {
        val user = hashMapOf<String, Any>(
            "nomeUsuario" to nomeUsuario
        )
        database.child("users").child(userId).updateChildren(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Nome atualizado com sucesso!", Toast.LENGTH_SHORT).show()


                    val intentRetorno = Intent(this, HomeActivity::class.java).apply {
                        putExtra("modelo", intent.getStringExtra("modelo"))
                        putExtra("imagem", intent.getIntExtra("imagem", R.drawable.sem_imagem))
                        putExtra("nomeUsuario", nomeUsuario)
                        putExtra("marca", intent.getStringExtra("marca"))
                        putExtra("plug", intent.getStringExtra("plug"))
                        putExtra("placa", intent.getStringExtra("placa"))
                    }


                    startActivity(intentRetorno)
                    finish()
                } else {
                    Toast.makeText(this, "Falha ao atualizar o nome", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
