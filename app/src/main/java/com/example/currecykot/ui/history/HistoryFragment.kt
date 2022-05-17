package com.example.currecykot.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currecykot.Currency
import com.example.currecykot.HistoryAdapter
import com.example.currecykot.R
import kotlinx.android.synthetic.main.fragment_history.*
import org.apache.commons.io.LineIterator
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class HistoryFragment : Fragment() {

    private val adapter = HistoryAdapter()
    var historyList: ArrayList<Currency> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_history, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            init()
        } catch (e: Exception) {
            val toast =
                Toast.makeText(requireContext(), "История не найдена", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    fun init() {

        recycler_history.layoutManager = LinearLayoutManager(requireContext())
        recycler_history.adapter = adapter
        readFile(File(requireActivity().filesDir, "hist.txt").path)

    }

    private fun addInRecyclerView() {
        var i = 0
        while (i < historyList.size) {
            val cur = Currency(historyList[i].charCode, historyList[i].name, historyList[i].value)
            adapter.addCurrency(cur)
            i++
        }
    }

    private fun readFile(pathname: String) {
        val file = File(pathname)
        var name = String()
        var value = String()
        var totalSum = String()
        val fr = FileReader(file)
        val reader = BufferedReader(fr)
        var line: String = String()
        val it: LineIterator = LineIterator(reader);
        while (it.hasNext()) {

            line = it.nextLine()
            name = line.substringBefore(',')
            value = line.substringAfter(',').substringBefore(",")
            totalSum = line.substringAfterLast(",")

            var currency = Currency(totalSum, name, value)
            historyList.add(currency)
            line = reader.readLine()
        }

        fr.close()
        addInRecyclerView()
    }
}