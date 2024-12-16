package br.edu.ifsp.dmo.atividadeavaliativa01.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import br.edu.ifsp.dmo.atividadeavaliativa01.model.WeightValue
import br.edu.ifsp.dmo.atividadeavaliativa01.R

class ValorPesoAdapter(private val context: Context): BaseAdapter() {
    override fun getCount(): Int {
        return WeightValue.values.size
    }

    override fun getItem(position: Int): Pair<Double, Double> {
        return Pair(WeightValue.values[position], WeightValue.pesos[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    // Retorna a view para cada membro da ListView
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.peso_valor, parent, false)

        val valueTextView = view.findViewById<TextView>(R.id.text_value)
        val weightTextView = view.findViewById<TextView>(R.id.text_weight)

        val (valor, peso) = getItem(position)
        valueTextView.text = "Valor: $valor"
        weightTextView.text = "Peso: $peso"

        return view
    }
}