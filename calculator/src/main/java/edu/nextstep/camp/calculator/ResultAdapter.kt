package edu.nextstep.camp.calculator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.dodobest.domain.ResultRecord
import edu.nextstep.camp.calculator.databinding.ItemResultBinding

class ResultAdapter : RecyclerView.Adapter<ResultAdapterViewHolder>() {
    private var results: List<ResultRecord> = emptyList()

    fun setResult(newResults: List<ResultRecord>) {
        results = newResults
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultAdapterViewHolder {
        val binding: ItemResultBinding = ItemResultBinding.inflate(LayoutInflater.from(parent.context))
        return ResultAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holderResultAdapter: ResultAdapterViewHolder, position: Int) {
        holderResultAdapter.expression.text = results[position].expression
        holderResultAdapter.result.text = results[position].result
    }

    override fun getItemCount(): Int {
        return results.size
    }
}