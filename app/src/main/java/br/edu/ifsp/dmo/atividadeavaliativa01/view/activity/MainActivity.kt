package br.edu.ifsp.dmo.atividadeavaliativa01.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.dmo.atividadeavaliativa01.R
import br.edu.ifsp.dmo.atividadeavaliativa01.databinding.ActivityMainBinding
import br.edu.ifsp.dmo.atividadeavaliativa01.model.WeightValue
import br.edu.ifsp.dmo.atividadeavaliativa01.view.adapters.ValorPesoAdapter
import br.edu.ifsp.dmo.atividadeavaliativa01.view.viewmodel.ValorPesoViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ValorPesoAdapter
    private lateinit var pesoLauncher: ActivityResultLauncher<Intent>

    private val pesoValorViewModel: ValorPesoViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Configura o layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicializa as listas de PesoValor
        WeightValue.values = ArrayList()
        WeightValue.pesos = ArrayList()

        WeightValue.values = pesoValorViewModel.valores
        WeightValue.pesos = pesoValorViewModel.pesos

        //Inicializa o Adapter para mostrar corretamente a ListView
        adapter = ValorPesoAdapter(this)
        binding.listviewValues.adapter = adapter

        //Configura o Laucher para a Intent Pesos
        pesoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val updatedPesos = result.data?.getDoubleArrayExtra("pesos")?.toList()
                if (updatedPesos != null) {
                    WeightValue.pesos = ArrayList(updatedPesos)
                    adapter.notifyDataSetChanged()
                }
            }
        }
        //Configura o que cada botao faz
        configListerners()
    }
    private fun configListerners() {
        binding.buttonColeta.setOnClickListener{startSelectionActivity(1)}
        binding.buttonPesos.setOnClickListener{startSelectionActivity(2)}
        binding.buttonAritimetica.setOnClickListener{startSelectionActivity(3)}
        binding.buttonPonderada.setOnClickListener{startSelectionActivity(4)}
        binding.buttonHarmonica.setOnClickListener{startSelectionActivity(5)}
    }

    private fun startSelectionActivity(n:Int){
        val mIntent: Intent
        if(n == 1){
            //Abre o dialogo para adicionar novo valor
            addValueDialog()
        }else if (n == 2){
            //Envia os valores e os pesos para mostrar na listView da Activity Pesos
            mIntent = Intent(this, PesoActivity::class.java)
            mIntent.putExtra("valores", WeightValue.values.toDoubleArray())
            mIntent.putExtra("pesos", WeightValue.pesos.toDoubleArray())
            pesoLauncher.launch(mIntent)
        }else if(n== 3 || n == 4 || n == 5){
            //Configura a intent para resultados e adicionas os valores
            mIntent = Intent(this, ResultadoActivity::class.java)
            mIntent.putExtra("valores", WeightValue.values.toDoubleArray())
            mIntent.putExtra("pesos", WeightValue.pesos.toDoubleArray())
            //Envia a estratégia correta escolhida pelo usuario
            if(n == 3){
                mIntent.putExtra("estrategia", "Aritimética")
            }else if(n==4){
                mIntent.putExtra("estrategia", "Ponderada")
            }else{
                mIntent.putExtra("estrategia", "Harmonica")
            }
            startActivity(mIntent)
        }
    }
    //Criação do dialogo para adicionar novo valor
    private fun addValueDialog() {
        val input = EditText(this)

        val dialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.valorTitulo))
            .setView(input)
            .setPositiveButton(getString(R.string.confirmar)) { _, _ ->
                val value = input.text.toString()
                if (value.isNotEmpty()) {
                    try {
                        val number = value.toDouble()
                        WeightValue.addVal(number)
                        adapter.notifyDataSetChanged()
                    } catch (e: NumberFormatException) {
                        showErrorDialog(getString(R.string.valorErro))
                    }
                }
            }
            .setNegativeButton(getString(R.string.cancelar), null)
            .create()

        dialog.show()
    }
    //Dialogo de erro
    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.error))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok), null)
            .create()
            .show()
    }
}