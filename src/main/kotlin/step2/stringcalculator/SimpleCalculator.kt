package step2.stringcalculator

class SimpleCalculator {

    fun evaluation(expression: String?): Double {

        val calculatorContext = CalculatorContext()
        val arguments = parseExpression(expression)
        arguments.forEach { arg ->
            when {
                arg.isDouble() -> calculatorContext.putNumber(arg)
                arg.isOperator() -> calculatorContext.putOperator(arg)
                else -> throw IllegalArgumentException("Invalid argument : $arg")
            }
        }

        return calculatorContext.complete()
    }

    private fun parseExpression(expression: String?): List<String> {
        val argumentList = expression?.split(" ")?.filter { it.isNotBlank() }?.map { it.trim() }
        if (argumentList.isNullOrEmpty()) {
            throw IllegalArgumentException("Expression is null or empty string")
        }
        return argumentList
    }

    private class CalculatorContext {
        private var result: Double? = null

        private var lastOperator: String? = null
        private var lastOperand: String? = null

        fun putNumber(operand: String) {

            val value = operand.toDouble()

            val currentResult = this.result
            val lastOperand = this.lastOperand
            val lastOperator = this.lastOperator

            this.result = when {
                currentResult == null -> value
                lastOperator != null -> calc(currentResult, value, lastOperator)
                else -> throw IllegalArgumentException("continuous number input found :  $lastOperand , $operand")
            }

            this.lastOperand = operand
            this.lastOperator = null
        }

        fun putOperator(operator: String) {
            if (this.lastOperator != null) {
                throw IllegalArgumentException("continuous operator input found  : $lastOperator , $operator")
            }
            this.lastOperator = operator
            this.lastOperand = null
        }

        fun complete(): Double {
            if (this.lastOperator != null) {
                throw IllegalArgumentException("Needless operator at the end of input : ${this.lastOperator}")
            }
            return this.result ?: 0.0
        }

        companion object {

            private fun calc(operand1: Double, operand2: Double, operator: String): Double {
                return when (operator) {
                    "+" -> operand1 + operand2
                    "-" -> operand1 - operand2
                    "*" -> operand1 * operand2
                    "/" -> if (operand2 != 0.0) operand1 / operand2 else throw IllegalArgumentException("Can't divide by 0")
                    else -> throw IllegalArgumentException("Invalid argument : $operator")
                }
            }
        }
    }
}
