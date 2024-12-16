//Retorna uma media Aritimetica
package br.edu.ifsp.dmo.atividadeavaliativa01.model

class ArithmeticStrategy : AverageStrategy {
    override fun Calcular(values: DoubleArray, Weight: DoubleArray): Double {
        return values.average();
    }
}