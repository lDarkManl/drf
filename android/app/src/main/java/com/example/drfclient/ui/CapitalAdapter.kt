package com.example.drfclient.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.drfclient.R
import com.example.drfclient.network.Capital

class CapitalAdapter : RecyclerView.Adapter<CapitalAdapter.VH>() {

	private val items: MutableList<Capital> = mutableListOf()

	fun submit(list: List<Capital>) {
		items.clear()
		items.addAll(list)
		notifyDataSetChanged()
	}

	fun prepend(item: Capital) {
		items.add(0, item)
		notifyItemInserted(0)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.item_capital, parent, false)
		return VH(view)
	}

	override fun onBindViewHolder(holder: VH, position: Int) {
		holder.bind(items[position])
	}

	override fun getItemCount(): Int = items.size

	class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
		private val textCity: TextView = itemView.findViewById(R.id.textCity)
		private val textMeta: TextView = itemView.findViewById(R.id.textMeta)

		fun bind(item: Capital) {
			textCity.text = "${item.capital_city} (${item.country})"
			textMeta.text = "Population: ${item.capital_population} • Author: ${item.author}"
		}
	}
}

