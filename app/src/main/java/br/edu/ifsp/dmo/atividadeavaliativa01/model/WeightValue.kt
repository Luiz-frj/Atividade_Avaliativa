package br.edu.ifsp.dmo.atividadeavaliativa01.model

object WeightValue {
    lateinit var values: ArrayList<Double>;
    lateinit var pesos: ArrayList<Double>;

    fun addVal(v:Double){
        values.add(v);
        pesos .add(1.0);
    }
}