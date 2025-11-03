package com.example.drfclient

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drfclient.databinding.ActivityMainBinding
import com.example.drfclient.network.Capital
import com.example.drfclient.ui.CapitalAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class MainActivity : ComponentActivity() {

	private lateinit var binding: ActivityMainBinding
	private val adapter = CapitalAdapter()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		binding.recycler.layoutManager = LinearLayoutManager(this)
		binding.recycler.adapter = adapter

		binding.buttonFetch.setOnClickListener {
			lifecycleScope.launch {
				try {
					val list = withContext(Dispatchers.IO) { ApiClient.fetchCapitals() }
					adapter.submit(list)
				} catch (t: Throwable) {
					showError(t)
				}
			}
		}

		binding.buttonAdd.setOnClickListener { showAddDialog() }
	}

	private fun showAddDialog() {
		val view = LayoutInflater.from(this).inflate(R.layout.dialog_add_capital, null)
		val inputCountry = view.findViewById<EditText>(R.id.inputCountry)
		val inputCity = view.findViewById<EditText>(R.id.inputCity)
		val inputPopulation = view.findViewById<EditText>(R.id.inputPopulation)
		val inputAuthor = view.findViewById<EditText>(R.id.inputAuthor)

		AlertDialog.Builder(this)
			.setTitle("Add capital")
			.setView(view)
			.setPositiveButton("Save") { dialog, _ ->
				val population = inputPopulation.text.toString().toIntOrNull() ?: 0
				val country = inputCountry.text.toString().trim()
				val city = inputCity.text.toString().trim()
				val author = inputAuthor.text.toString().trim()
				if (country.isEmpty() || city.isEmpty() || author.isEmpty() || population <= 0) {
					showErrorMessage("Fill all fields. Population must be > 0")
					return@setPositiveButton
				}
				val body = Capital(
					capital_city = city,
					capital_population = population,
					author = author,
					country = country,
				)
				lifecycleScope.launch {
					try {
						val created = withContext(Dispatchers.IO) { ApiClient.addCapital(body) }
						adapter.prepend(created)
					} catch (t: Throwable) {
						showError(t)
					}
				}
				dialog.dismiss()
			}
			.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
			.show()
	}

	private fun showError(t: Throwable) {
		val message = if (t is HttpException) {
			val raw = try { t.response()?.errorBody()?.string() } catch (_: Throwable) { null }
			if (!raw.isNullOrBlank()) raw else "HTTP ${t.code()} ${t.message()}"
		} else t.message ?: t.toString()
		showErrorMessage(message)
	}

	private fun showErrorMessage(msg: String) {
		AlertDialog.Builder(this)
			.setTitle("Error")
			.setMessage(msg)
			.setPositiveButton("OK", null)
			.show()
	}
}

