package com.curiozing.unitConverter

class Utils {

    companion object {

        fun convertTheUnit(
            selectedInputConverter: String,
            selectedOutputConverter: String,
            value: Double
        ): Double {
            val result = when (selectedInputConverter to selectedOutputConverter) {
                "Centimetre" to "Metre" -> value / 100
                "Centimetre" to "Feet" -> value / 30.48
                "Centimetre" to "Millimetre" -> value * 10
                "Metre" to "Centimetre" -> value * 100
                "Metre" to "Feet" -> value * 3.281
                "Metre" to "Millimetre" -> value * 1000
                "Feet" to "Centimetre" -> value * 30.48
                "Feet" to "Metre" -> value / 3.281
                "Feet" to "Millimetre" -> value * 304.8
                "Millimetre" to "Centimetre" -> value / 10
                "Millimetre" to "Metre" -> value / 1000
                "Millimetre" to "Feet" -> value / 304.8
                else -> 0.0
            }
            return result
        }

    }

}