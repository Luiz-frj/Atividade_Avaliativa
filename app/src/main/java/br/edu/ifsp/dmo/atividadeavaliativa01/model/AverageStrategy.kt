package br.edu.ifsp.dmo.atividadeavaliativa01.model

interface AverageStrategy {
    fun Calcular(values: DoubleArray, Weight: DoubleArray): Double
}