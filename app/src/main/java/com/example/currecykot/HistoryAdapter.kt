package com.example.currecykot

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currecykot.databinding.CurrencyItemBinding

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryHolder>() {
    val currencyList = ArrayList<Currency>()

    class HistoryHolder(item: View) : RecyclerView.ViewHolder(item) {

        val binding = CurrencyItemBinding.bind(item)

        @SuppressLint("SetTextI18n")
        fun bind(currency: Currency) = with(binding) {

            nameHist.text = currency.name + "\n" + currency.value
            valueHist.text = currency.charCode

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.currency_item, parent, false)
        return HistoryHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        holder.bind(currencyList[position])
    }

    override fun getItemCount(): Int {
        return currencyList.size
    }

    fun addCurrency(currency: Currency) {
        currencyList.add(currency)
        notifyDataSetChanged()
    }

}

