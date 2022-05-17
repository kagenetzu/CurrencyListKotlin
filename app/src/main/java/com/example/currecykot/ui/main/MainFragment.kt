package com.example.currecykot.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.currecykot.*
import com.example.currecykot.Currency
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


class MainFragment : Fragment() {

    lateinit var currencySpinner: Spinner
    var currencyList: ArrayList<Currency> = ArrayList()

    val client = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_main, container, false)

    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView: TextView = view.findViewById(R.id.convertRuble)
        val editText: EditText = view.findViewById(R.id.inputRuble)
        val button: Button = view.findViewById(R.id.button)
        val button2: Button = view.findViewById(R.id.button2)

        currencySpinner = view.findViewById(R.id.spinner)
        run("https://www.cbr-xml-daily.ru/daily_json.js")

        button.setOnClickListener {
            try {
                if (editText.getText().toString().toDouble() > 0 && editText.getText()
                        .toString() !== ""
                ) {

                    val result: Currency =
                        currencySpinner.adapter.getItem(currencySpinner.firstVisiblePosition) as Currency
                    textView.text = String.format(
                        Locale.ROOT, "%.2f",
                        result.value!!.toDouble() * editText.text.toString().toDouble()
                    ) + " руб."

                    saveFile(result.name + ",Курс:" + result.value + " | Кол-во:${editText.text}" + ",Сумма:" + textView.text + "\n")
                }
            } catch (e: Exception) {
                val toast =
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT)
                toast.show()
            }
        }
        button2.setOnClickListener {
            try {

                clearFile("")

            } catch (e: Exception) {
                val toast =
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT)
                toast.show()
            }
        }
    }

    private fun saveFile(text: String) {
        requireActivity().openFileOutput("hist.txt", Context.MODE_APPEND).use {
            it.write(text.toByteArray())
            it.write(System.getProperty("line.separator").toByteArray())
        }
    }

    private fun clearFile(text: String) {
        requireActivity().openFileOutput("hist.txt", Context.MODE_PRIVATE).use {
            it.write(text.toByteArray())
        }
    }


    fun run(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
                var str_response = response.body()!!.string()
                val json_contact: JSONObject = JSONObject(str_response)
                var jsonMain = JSONObject(str_response)
                var charCode = JSONObject(str_response)
                jsonMain = jsonMain.getJSONObject("Valute")
                charCode = charCode.getJSONObject("Valute")

                var temp: JSONObject? = null
                while (charCode.keys().hasNext()) {
                    temp = jsonMain.getJSONObject(charCode.keys().next())
                    var Currency: Currency = Currency()
                    Currency.charCode = temp.getString("CharCode")
                    Currency.name = temp.getString("Name")
                    Currency.value = temp.getString("Value")
                    currencyList.add(Currency)
                    charCode.remove(charCode.keys().next())
                }

                activity!!.runOnUiThread {
                    val curAdapter: CurrencyAdapter
                    curAdapter = CurrencyAdapter(requireContext(), currencyList)
                    currencySpinner.adapter = curAdapter
                }
            }
        })
    }

}


