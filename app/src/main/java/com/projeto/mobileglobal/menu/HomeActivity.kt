package com.projeto.mobileglobal.menu

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.projeto.mobileglobal.R

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val imagemProvisoria = findViewById<ImageView>(R.id.imagem_provisoria)
        val nomeCliente = findViewById<TextView>(R.id.nome_cliente) // TextView do "Olá"

        // Receber dados do Intent
        val modelo = intent.getStringExtra("modelo")
        val imagemResId = intent.getIntExtra("imagem", R.drawable.sem_imagem)
        val email = intent.getStringExtra("email")
        val nomeUsuario = intent.getStringExtra("nomeUsuario")
        val marca = intent.getStringExtra("marca")
        val plug = intent.getStringExtra("plug")
        val placa = intent.getStringExtra("placa")

        // Atualizar a imagem do veículo, se disponível
        if (modelo != null) {
            imagemProvisoria.setImageResource(imagemResId)
        }

        // Atualizar o texto "Olá"
        if (nomeUsuario != null) {
            nomeCliente.text = getString(R.string.saudacao_usuario, nomeUsuario)
        }

        // Configurar o card "Meu Veículo"
        val cardMeuVeiculo = findViewById<CardView>(R.id.card_meu_veiculo)
        cardMeuVeiculo.setOnClickListener {
            val intent = Intent(this, MeuVeiculoActivity::class.java).apply {
                putExtra("modelo", modelo)
                putExtra("imagem", imagemResId)
                putExtra("nomeUsuario", nomeUsuario)
                putExtra("marca", marca)
                putExtra("plug", plug)
                putExtra("placa", placa)
            }
            startActivity(intent)
        }

        val cardEletroponto = findViewById<CardView>(R.id.card_eletroponto)
        cardEletroponto.setOnClickListener {
            val intent = Intent(this, EletropontoActivity::class.java).apply {
                putExtra("modelo", modelo)
                putExtra("imagem", imagemResId)
                putExtra("nomeUsuario", nomeUsuario)
                putExtra("marca", marca)
                putExtra("plug", plug)
                putExtra("placa", placa)
            }
            startActivity(intent)
        }

        val cardMinhasReservas = findViewById<CardView>(R.id.card_minhas_reservas)
        cardMinhasReservas.setOnClickListener {
            val intent = Intent(this, MinhasReservasActivity::class.java).apply {
                putExtra("modelo", modelo)
                putExtra("imagem", imagemResId)
                putExtra("nomeUsuario", nomeUsuario)
                putExtra("marca", marca)
                putExtra("plug", plug)
                putExtra("placa", placa)
            }
            startActivity(intent)
        }

        val cardMeuPerfil = findViewById<CardView>(R.id.card_meu_perfil)
        cardMeuPerfil.setOnClickListener {
            val intent = Intent(this, PerfilActivity::class.java).apply {
                putExtra("modelo", modelo)
                putExtra("imagem", imagemResId)
                putExtra("nomeUsuario", nomeUsuario)
                putExtra("email", email)
                putExtra("marca", marca)
                putExtra("plug", plug)
                putExtra("placa", placa)
            }
            startActivity(intent)
        }

    }
}

