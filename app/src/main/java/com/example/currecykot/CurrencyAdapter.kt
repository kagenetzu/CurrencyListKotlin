package com.example.currecykot

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class CurrencyAdapter : BaseAdapter {
    var ctx: Context
    var lInflater: LayoutInflater
    var objects: List<Currency>? = null

    internal constructor(context: Context) {
        ctx = context
        lInflater = ctx
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    internal constructor(context: Context, currencyList: List<Currency>?) {
        ctx = context
        objects = currencyList
        lInflater = ctx
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return objects!!.size
    }

    override fun getItem(position: Int): Any {
        return objects!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = lInflater.inflate(R.layout.adapter_layout, parent, false)
        }
        val c = getCurrency(position)
        (view!!.findViewById<View>(R.id.name) as TextView).text = c.name
        (view.findViewById<View>(R.id.charCode) as TextView).text = (c.charCode + " | " + c.value)
        return view
    }

    private fun getCurrency(position: Int): Currency {
        return getItem(position) as Currency
    }
}