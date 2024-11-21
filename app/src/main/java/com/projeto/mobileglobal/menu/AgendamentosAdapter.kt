package com.projeto.mobileglobal.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.projeto.mobileglobal.R

class AgendamentosAdapter(
    private val agendamentos: MutableList<String>,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<AgendamentosAdapter.AgendamentoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgendamentoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_agendamento, parent, false)
        return AgendamentoViewHolder(view)
    }

    override fun onBindViewHolder(holder: AgendamentoViewHolder, position: Int) {
        val agendamento = agendamentos[position]
        holder.bind(agendamento, position)
    }

    override fun getItemCount(): Int = agendamentos.size

    inner class AgendamentoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewAgendamento: TextView = itemView.findViewById(R.id.textViewAgendamento)
        private val btnExcluir: Button = itemView.findViewById(R.id.btnExcluir)

        fun bind(agendamento: String, position: Int) {
            textViewAgendamento.text = agendamento
            btnExcluir.setOnClickListener { onDeleteClick(position) }
        }
    }
}
