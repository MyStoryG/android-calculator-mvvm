package edu.nextstep.camp.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.nextstep.camp.calculator.domain.Calculator
import edu.nextstep.camp.calculator.domain.Expression
import edu.nextstep.camp.calculator.domain.HistoryRepository
import edu.nextstep.camp.calculator.domain.Operator
import edu.nextstep.camp.calculator.domain.model.Memory
import kotlinx.coroutines.launch

class CalculatorViewModel(
    private val calculator: Calculator = Calculator(),
    expression: Expression = Expression.EMPTY,
    private val historyRepository: HistoryRepository
) : ViewModel() {

    private val _expression = MutableLiveData<Expression>(expression)
    val expression: LiveData<Expression>
        get() = _expression

    private val _expressionError = MutableLiveData<Event<Unit>>()
    val expressionError: LiveData<Event<Unit>>
        get() = _expressionError

    private val _memoryVisible = MutableLiveData<Boolean>(false)
    val memoryVisible: LiveData<Boolean>
        get() = _memoryVisible

    private val _expressionMemory = MutableLiveData<List<Memory>>(emptyList())
    val expressionMemory: LiveData<List<Memory>>
        get() = _expressionMemory

    init {
        viewModelScope.launch {
            _expressionMemory.value = historyRepository.getAll()
        }
    }

    fun addToExpression(operand: Int) {
        _expression.value = _expression.value?.plus(operand) ?: Expression.EMPTY + operand
    }

    fun addToExpression(operator: Operator) {
        _expression.value = _expression.value?.plus(operator)
    }

    fun removeLast() {
        _expression.value = expression.value?.removeLast()
    }

    fun calculate() {
        val expression = expression.value.toString()
        val result = calculator.calculate(expression)
        if (result == null) {
            _expressionError.value = Event(Unit)
            return
        }
        _expression.value = Expression.EMPTY + result

        val newHistory = Memory(expression, result.toString())
        viewModelScope.launch {
            historyRepository.insert(newHistory)
            _expressionMemory.value = historyRepository.getAll()
        }

    }

    fun showAndHideHistoryList() {
        val isVisible = memoryVisible.value ?: false
        _memoryVisible.value = !isVisible
    }
}