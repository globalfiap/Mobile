package com.projeto.mobileglobal.menu

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.projeto.mobileglobal.R
import com.projeto.mobileglobal.endereco.PlacesData
import java.util.*

class MinhasReservasActivity : AppCompatActivity() {
    private var selectedDate: String = ""
    private var selectedTime: String = ""
    private val agendamentos = mutableListOf<String>()
    private lateinit var adapterRecyclerView: AgendamentosAdapter

    private var modelo: String? = null
    private var imagemResId: Int = R.drawable.sem_imagem
    private var nomeUsuario: String? = null
    private var marca: String? = null
    private var plug: String? = null
    private var placa: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_minhas_reservas)

        // Recuperar dados do Intent
        modelo = intent.getStringExtra("modelo")
        imagemResId = intent.getIntExtra("imagem", R.drawable.sem_imagem)
        nomeUsuario = intent.getStringExtra("nomeUsuario")
        marca = intent.getStringExtra("marca")
        plug = intent.getStringExtra("plug")
        placa = intent.getStringExtra("placa")

        val spinnerEletropontos: Spinner = findViewById(R.id.spinnerEletropontos)
        val btnSelecionarData: Button = findViewById(R.id.btnSelecionarData)
        val btnSelecionarHora: Button = findViewById(R.id.btnSelecionarHora)
        val btnAgendar: Button = findViewById(R.id.btnAgendar)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewAgendamentos)

        val placeNames = PlacesData.places.map { it.name }
        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, placeNames)
        spinnerEletropontos.adapter = adapterSpinner

        adapterRecyclerView = AgendamentosAdapter(agendamentos) { position ->
            agendamentos.removeAt(position)
            adapterRecyclerView.notifyItemRemoved(position)
            Toast.makeText(this, "Agendamento excluído.", Toast.LENGTH_SHORT).show()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapterRecyclerView

        btnSelecionarData.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                Toast.makeText(this, "Data Selecionada: $selectedDate", Toast.LENGTH_SHORT).show()
            }, year, month, day).show()
        }

        btnSelecionarHora.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                Toast.makeText(this, "Hora Selecionada: $selectedTime", Toast.LENGTH_SHORT).show()
            }, hour, minute, true).show()
        }

        btnAgendar.setOnClickListener {
            val selectedPlaceName = spinnerEletropontos.selectedItem.toString()
            val selectedPlace = PlacesData.places.find { it.name == selectedPlaceName }

            if (selectedPlace != null) {
                val agendamento = """
                    Eletroponto: ${selectedPlace.name}
                    Data: $selectedDate
                    Hora: $selectedTime
                """.trimIndent()

                agendamentos.add(agendamento)
                adapterRecyclerView.notifyItemInserted(agendamentos.size - 1)

                Toast.makeText(
                    this,
                    "Agendamento confirmado:\n$agendamento",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(this, "Erro ao encontrar o lugar selecionado.", Toast.LENGTH_SHORT).show()
            }
        }

        // Botão de voltar
        val setaVoltar = findViewById<ImageView>(R.id.voltar)
        setaVoltar.setOnClickListener {
            val intentRetorno = Intent(this, HomeActivity::class.java).apply {
                putExtra("modelo", modelo)
                putExtra("imagem", imagemResId)
                putExtra("nomeUsuario", nomeUsuario)
                putExtra("marca", marca)
                putExtra("plug", plug)
                putExtra("placa", placa)
            }
            startActivity(intentRetorno)
            finish()
        }
    }
}
