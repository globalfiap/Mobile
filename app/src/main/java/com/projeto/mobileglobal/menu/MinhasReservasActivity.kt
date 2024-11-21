package com.projeto.mobileglobal.menu

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
    private val agendamentos = mutableListOf<String>() // Lista para armazenar agendamentos
    private lateinit var adapterRecyclerView: AgendamentosAdapter // Adapter da RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_minhas_reservas)

        val spinnerEletropontos: Spinner = findViewById(R.id.spinnerEletropontos)
        val btnSelecionarData: Button = findViewById(R.id.btnSelecionarData)
        val btnSelecionarHora: Button = findViewById(R.id.btnSelecionarHora)
        val btnAgendar: Button = findViewById(R.id.btnAgendar)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewAgendamentos)



        // Dados para o Spinner
        val placeNames = PlacesData.places.map { it.name }
        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, placeNames)
        spinnerEletropontos.adapter = adapterSpinner

        // Inicialização do Adapter da RecyclerView
        adapterRecyclerView = AgendamentosAdapter(agendamentos) { position ->
            agendamentos.removeAt(position)
            adapterRecyclerView.notifyItemRemoved(position)
            Toast.makeText(this, "Agendamento excluído.", Toast.LENGTH_SHORT).show()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapterRecyclerView

        // Configuração do botão Selecionar Data
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

        // Configuração do botão Selecionar Hora
        btnSelecionarHora.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                Toast.makeText(this, "Hora Selecionada: $selectedTime", Toast.LENGTH_SHORT).show()
            }, hour, minute, true).show()
        }

        // Configuração do botão Agendar
        btnAgendar.setOnClickListener {
            val selectedPlaceName = spinnerEletropontos.selectedItem.toString()
            val selectedPlace = PlacesData.places.find { it.name == selectedPlaceName }

            if (selectedPlace != null) {
                val agendamento = """
                    Eletroponto: ${selectedPlace.name}
                    Data: $selectedDate
                    Hora: $selectedTime
                """.trimIndent()

                agendamentos.add(agendamento) // Adiciona o agendamento à lista
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
    }
}
