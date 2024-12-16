package br.edu.ifsp.dmo.atividadeavaliativa01.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.dmo.atividadeavaliativa01.model.ArithmeticStrategy
import br.edu.ifsp.dmo.atividadeavaliativa01.model.AverageStrategy
import br.edu.ifsp.dmo.atividadeavaliativa01.model.HarmonicaStrategy
import br.edu.ifsp.dmo.atividadeavaliativa01.model.WeightedStrategy
import br.edu.ifsp.dmo.atividadeavaliativa01.R
import br.edu.ifsp.dmo.atividadeavaliativa01.databinding.ActivityResultadoBinding

class ResultadoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultadoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        openBundle()
    }

    private fun openBundle() {
        val valores = intent.getDoubleArrayExtra("valores")
        val pesos = intent?.getDoubleArrayExtra("pesos")
        val estrategia = intent.getStringExtra("estrategia")
        if (estrategia != null && pesos != null && valores != null) {
            if(estrategia.equals("Aritimética")){
                handleMedia(1, valores, pesos)
            }
            if(estrategia.equals("Ponderada")){
                handleMedia(2, valores, pesos)
            }
            if(estrategia.equals("Harmonica")){
                handleMedia(3, valores, pesos)
            }
        }
    }
    //Passa o tipo de estrategia e os valores, assim colocando o tipo e os valores finais, além do link para saber mais
    private fun handleMedia(r: Int, valores: DoubleArray, pesos: DoubleArray){
        var url:String
        val strategy:AverageStrategy
        if(r == 1){
            url = "https://www.youtube.com/watch?v=QS6sdNaIEo8"
            strategy = ArithmeticStrategy()
            binding.rstTipo.text = getString(R.string.aritimetica)
            binding.rstAjuda.text = getString(R.string.explicacao_aritimetica)
        }else if(r == 2){
            url = "https://www.youtube.com/watch?v=xkHf8L0eTgU"
            strategy = WeightedStrategy()
            binding.rstTipo.text = getString(R.string.ponderada)
            binding.rstAjuda.text = getString(R.string.explicacao_ponderada)

        }else if(r == 3){
            url = "https://www.youtube.com/watch?v=17AW2znpYmU"
            strategy = HarmonicaStrategy();
            binding.rstTipo.text = getString(R.string.harmonica)
            binding.rstAjuda.text = getString(R.string.explicacao_harmonica)
        }else{
            return
        }
        val mIntent= Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
        //Começa a atividade no navegador
        binding.rstLink.setOnClickListener{
            startActivity(mIntent)
        }
        binding.rstValor.text = "Valor: ${strategy.Calcular(valores, pesos)}"
    }
}