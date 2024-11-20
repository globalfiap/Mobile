package com.projeto.mobileglobal.menu

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.projeto.mobileglobal.R

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val imagemProvisoria = findViewById<ImageView>(R.id.imagem_provisoria)

        // Receber dados do Intent
        val modelo = intent.getStringExtra("modelo")
        val imagemResId = intent.getIntExtra("imagem", R.drawable.sem_imagem)

        // Atualizar a imagem do veículo, se disponível
        if (modelo != null) {
            imagemProvisoria.setImageResource(imagemResId)
        }

        val cardEletroponto = findViewById<CardView>(R.id.card_eletroponto)
        cardEletroponto.setOnClickListener {
            val intent = Intent(this, EletropontoActivity::class.java)
            startActivity(intent)
        }

        val cardMinhasReservas = findViewById<CardView>(R.id.card_minhas_reservas)
        cardMinhasReservas.setOnClickListener {
            val intent = Intent(this, MinhasReservasActivity::class.java)
            startActivity(intent)
        }

        val cardMeuVeiculo = findViewById<CardView>(R.id.card_meu_veiculo)
        cardMeuVeiculo.setOnClickListener {
            val intent = Intent(this, MeuVeiculoActivity::class.java)
            startActivity(intent)
        }

        val cardMeuPerfil = findViewById<CardView>(R.id.card_meu_perfil)
        cardMeuPerfil.setOnClickListener {
            val intent = Intent(this, PerfilActivity::class.java)
            startActivity(intent)
        }
    }
}
