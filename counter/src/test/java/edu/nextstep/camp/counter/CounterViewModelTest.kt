package edu.nextstep.camp.counter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test

internal class CounterViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val viewModel: CounterViewModel = CounterViewModel()

    @Test
    fun `UP 버튼을 클릭하면 숫자가 1 증가해야 한다`() {
        // given
        val givenCount = viewModel.counter.getOrAwaitValue()

        // when
        viewModel.increase()

        // then
        assertThat(viewModel.counter.getOrAwaitValue()).isEqualTo(givenCount + 1)
    }

    @Test
    fun `DOWN 버튼을 클릭하면 숫자가 1 감소해야 한다`() {
        // given
        viewModel.increase()
        val givenCount = viewModel.counter.getOrAwaitValue()

        // when
        viewModel.decrease()

        // then
        assertThat(viewModel.counter.getOrAwaitValue()).isEqualTo(givenCount - 1)
    }

    @Test
    fun `숫자가 0일 때, DOWN 버튼을 클릭하면 0 이하로 내릴 수 없습니다 토스트 메시지가 보여야 한다`() {

        // when
        viewModel.decrease()

        // then
        assertThat(viewModel.countFailed.getOrAwaitValue()).isEqualTo(true)
    }
}
