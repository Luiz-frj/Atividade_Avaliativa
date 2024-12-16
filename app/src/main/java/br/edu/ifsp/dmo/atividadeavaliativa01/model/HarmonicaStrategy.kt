//Retorna uma media Harmonica
package br.edu.ifsp.dmo.atividadeavaliativa01.model

class HarmonicaStrategy : AverageStrategy {
    override fun Calcular(values: DoubleArray, Weight: DoubleArray): Double {
        var i =0;
        var sum = 0.0;

        while (i < values.size){
            sum += 1/values[i];
            i++;
        }

        return values.size/sum;
    }

}