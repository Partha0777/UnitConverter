package com.curiozing.unitConverter

class Utils {

    companion object {

        fun convertTheUnit(
            selectedInputConverter: String,
            selectedOutputConverter: String,
            value: Double
        ): Double = when (selectedInputConverter) {
            "Centimetre" -> when (selectedOutputConverter) {
                "Metre" -> value / 100
                "Feet" -> value / 30.48
                "Millimetre" -> value * 10
                else -> 0.0
            }
            "Metre" -> when (selectedOutputConverter) {
                "Centimetre" -> value * 100
                "Feet" -> value * 3.281
                "Millimetre" -> value * 1000
                else -> 0.0
            }
            "Feet" -> when (selectedOutputConverter) {
                "Centimetre" -> value * 30.48
                "Metre" -> value / 3.281
                "Millimetre" -> value * 304.8
                else -> 0.0
            }
            "Millimetre" -> when (selectedOutputConverter) {
                "Centimetre" -> value / 10
                "Metre" -> value / 1000
                "Feet" -> value / 304.8
                else -> 0.0
            }
            else -> 0.0
        }
    }

}