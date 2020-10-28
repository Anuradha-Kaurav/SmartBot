package com.chatbot.smartbot.utils

object AlgorithmMathSolution {

    fun solveEquation(equation: String): Int{

        val newEquation = equation.replace(" ", "")

        return when{
            newEquation.contains("+") -> {
                val subEquation = newEquation.split("+")
                return subEquation[0].toInt() + subEquation[1].toInt()
            }
            newEquation.contains("-") -> {
                val subEquation = newEquation.split("-")
                return subEquation[0].toInt() - subEquation[1].toInt()
            }
            newEquation.contains("*") -> {
                val subEquation = newEquation.split("*")
                return subEquation[0].toInt() * subEquation[1].toInt()
            }
            newEquation.contains("/") -> {
                val subEquation = newEquation.split("/")
                return subEquation[0].toInt() / subEquation[1].toInt()
            }
            else -> {
                0
            }
        }
    }
}