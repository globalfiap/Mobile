package com.projeto.mobileglobal.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.projeto.mobileglobal.R
import com.projeto.mobileglobal.menu.HomeActivity

class CadastroVeiculoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_veiculo)

        val spinnerMarca = findViewById<Spinner>(R.id.spinnermarca)
        val spinnerModelo = findViewById<Spinner>(R.id.spinnermodelo)
        val spinnerPlugs = findViewById<Spinner>(R.id.spinnerplugs)
        val editTextPlaca = findViewById<EditText>(R.id.editTextPlaca)
        val buttonCadastrar = findViewById<Button>(R.id.buttonCadastrar)

        // Mapa para associar modelos de veículos às suas imagens
        val vehicleImages = mapOf(
            "BYD Dolphin" to R.drawable.car_dolphin,
            "BYD Song Pro" to R.drawable.car_song,
            "BYD Seal" to R.drawable.car_seal,
            "Tesla Model 3" to R.drawable.car_model3,
            "Tesla Cybertruck" to R.drawable.car_cybertruck,
            "Volvo C40" to R.drawable.car_c40
        )

        // Preencher opções de marca
        val marcas = listOf("BYD", "Tesla", "Volvo")
        val adapterMarca = ArrayAdapter(this, android.R.layout.simple_spinner_item, marcas)
        adapterMarca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMarca.adapter = adapterMarca

        // Atualizar modelos com base na marca selecionada
        spinnerMarca.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val modelos = when (marcas[position]) {
                    "BYD" -> listOf("BYD Dolphin", "BYD Song Pro", "BYD Seal")
                    "Tesla" -> listOf("Tesla Model 3", "Tesla Cybertruck")
                    "Volvo" -> listOf("Volvo C40")
                    else -> emptyList()
                }
                val adapterModelo = ArrayAdapter(
                    this@CadastroVeiculoActivity,
                    android.R.layout.simple_spinner_item,
                    modelos
                )
                adapterModelo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerModelo.adapter = adapterModelo
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Preencher opções de plugs
        val plugs = listOf("Tipo 1", "Tipo 2", "GB/T", "CCS")
        val adapterPlugs = ArrayAdapter(this, android.R.layout.simple_spinner_item, plugs)
        adapterPlugs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPlugs.adapter = adapterPlugs

        // Botão de cadastro
        buttonCadastrar.setOnClickListener {
            val marca = spinnerMarca.selectedItem?.toString()
            val modelo = spinnerModelo.selectedItem?.toString()
            val plug = spinnerPlugs.selectedItem?.toString()
            val placa = editTextPlaca.text.toString()

            if (marca != null && modelo != null && plug != null && placa.isNotEmpty()) {
                Toast.makeText(this, "Veículo cadastrado com sucesso!", Toast.LENGTH_SHORT).show()

                // Passar dados do veículo para a HomeActivity
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("modelo", modelo)
                intent.putExtra("imagem", vehicleImages[modelo])
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Por favor, preencha todas as informações.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
