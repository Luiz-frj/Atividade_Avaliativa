//Retorna a media ponderada
package br.edu.ifsp.dmo.atividadeavaliativa01.model

class WeightedStrategy : AverageStrategy {
    override fun Calcular(values: DoubleArray, Weight: DoubleArray): Double {
        var i =0;
        var sum = 0.0;
        var division = 0.0;

        while (i < values.size){
            sum += values [i] * Weight[i];
            division += Weight[i];
            i++;
        }

        return sum/division;
    }
}