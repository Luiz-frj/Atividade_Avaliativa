package br.edu.ifsp.dmo.atividadeavaliativa01.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.dmo.atividadeavaliativa01.R
import br.edu.ifsp.dmo.atividadeavaliativa01.databinding.ActivityPesosBinding
import br.edu.ifsp.dmo.atividadeavaliativa01.model.WeightValue
import br.edu.ifsp.dmo.atividadeavaliativa01.view.adapters.ValorPesoAdapter

class PesoActivity : AppCompatActivity(){
    private lateinit var binding: ActivityPesosBinding
    private lateinit var adapter: ValorPesoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPesosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val valores = intent.getDoubleArrayExtra("valores")?.toList() ?: listOf()
        WeightValue.values = ArrayList(valores)
        val pesos = intent.getDoubleArrayExtra("pesos")?.toList() ?: listOf()
        WeightValue.pesos = ArrayList(pesos)

        adapter = ValorPesoAdapter(this)
        binding.listviewPesos.adapter = adapter

        binding.listviewPesos.setOnItemClickListener { _, _, position, _ ->
            showEditWeightDialog(position)
        }

        configButtons()
    }

    private fun configButtons() {
        binding.buttonSave.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("pesos", WeightValue.pesos.toDoubleArray())
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
        binding.buttonCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    private fun showEditWeightDialog(position: Int) {
        val input = EditText(this)
        input.setText(WeightValue.pesos[position].toString())

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.editarPeso))
            .setView(input)
            .setPositiveButton(getString(R.string.confirmar)) { _, _ ->
                val novoPeso = input.text.toString().toDoubleOrNull()
                if (novoPeso != null) {
                    WeightValue.pesos[position] = novoPeso
                    adapter.notifyDataSetChanged()
                } else {
                    showErrorDialog(getString(R.string.pesoErro))
                }
            }
            .setNegativeButton(getString(R.string.cancelar), null)
            .create()
            .show()
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.error))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok), null)
            .create()
            .show()
    }
}