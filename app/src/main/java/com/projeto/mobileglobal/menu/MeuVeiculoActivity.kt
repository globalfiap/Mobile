package com.projeto.mobileglobal.menu

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.projeto.mobileglobal.R

class MeuVeiculoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meu_veiculo)


        val setaVoltar = findViewById<ImageView>(R.id.imageView4)
        setaVoltar.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java).apply {
                putExtra("modelo", intent.getStringExtra("modelo"))
                putExtra("imagem", intent.getIntExtra("imagem", R.drawable.sem_imagem))
                putExtra("nomeUsuario", intent.getStringExtra("nomeUsuario"))
                putExtra("marca", intent.getStringExtra("marca"))
                putExtra("plug", intent.getStringExtra("plug"))
                putExtra("placa", intent.getStringExtra("placa"))
            }
            startActivity(intent)
            finish()
        }

        // Receber dados do Intent
        val modelo = intent.getStringExtra("modelo")
        val marca = intent.getStringExtra("marca")
        val plug = intent.getStringExtra("plug")
        val placa = intent.getStringExtra("placa")
        val imagemResId = intent.getIntExtra("imagem", R.drawable.sem_imagem)

        // Vincular componentes da interface
        val imagemVeiculo = findViewById<ImageView>(R.id.imagem_veiculo)
        val textoModelo = findViewById<TextView>(R.id.texto_modelo)
        val textoMarca = findViewById<TextView>(R.id.texto_marca)
        val textoPlug = findViewById<TextView>(R.id.texto_plug)
        val textoPlaca = findViewById<TextView>(R.id.texto_placa)

// Configurar os valores nos componentes usando recursos de string
        imagemVeiculo.setImageResource(imagemResId)
        textoModelo.text = getString(R.string.modelo_texto, modelo)
        textoMarca.text = getString(R.string.marca_texto, marca)
        textoPlug.text = getString(R.string.plug_texto, plug)
        textoPlaca.text = getString(R.string.placa_texto, placa)
    }
}
