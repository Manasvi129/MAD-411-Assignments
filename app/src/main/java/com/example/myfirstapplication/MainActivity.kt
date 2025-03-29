package com.example.myfirstapplication

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var expenseNameEditText: EditText
    private lateinit var amountEditText: EditText
    private lateinit var dateEditText: EditText
    private lateinit var addExpenseButton: Button
    private lateinit var expensesRecyclerView: RecyclerView
    private lateinit var expenseAdapter: ExpenseAdapter
    private val expensesList = ArrayList<Expense>()
    private lateinit var footerFragment: FooterFragment
    private val FILE_NAME = "expenses.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load expenses from file
        loadExpensesFromFile()

        // Initialize UI components
        expenseNameEditText = findViewById(R.id.expenseNameEditText)
        amountEditText = findViewById(R.id.amountEditText)
        dateEditText = findViewById(R.id.dateEditText)
        addExpenseButton = findViewById(R.id.addExpenseButton)
        expensesRecyclerView = findViewById(R.id.expensesRecyclerView)

        // Add Header and Footer Fragments dynamically
        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        val headerFragment = HeaderFragment()
        footerFragment = FooterFragment()

        transaction.add(R.id.headerContainer, headerFragment)
        transaction.add(R.id.footerContainer, footerFragment)
        transaction.commit()

        // RecyclerView setup
        expensesRecyclerView.layoutManager = LinearLayoutManager(this)
        expenseAdapter = ExpenseAdapter(this, expensesList, footerFragment)
        expensesRecyclerView.adapter = expenseAdapter

        // Add Expense button listener
        addExpenseButton.setOnClickListener {
            addExpenseClick()
        }
    }

    private fun addExpenseClick() {
        val name = expenseNameEditText.text.toString()
        val amountString = amountEditText.text.toString()
        val date = dateEditText.text.toString()

        val amount = amountString.toDoubleOrNull() ?: return
        val expense = Expense(name, amount, date)

        expenseAdapter.addExpense(expense)

        // Clear input fields after adding expense
        expenseNameEditText.text.clear()
        amountEditText.text.clear()
        dateEditText.text.clear()
    }

    // Save expenses to a file using Gson
    fun saveExpensesToFile() {
        val gson = Gson()
        val json = gson.toJson(expensesList)

        try {
            openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use { output ->
                output.write(json.toByteArray())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    // Load expenses from a file using Gson
    private fun loadExpensesFromFile() {
        try {
            val file = File(filesDir, FILE_NAME)
            if (!file.exists()) return

            val json = file.readText()
            val gson = Gson()
            val type = object : TypeToken<List<Expense>>() {}.type
            val loadedExpenses: List<Expense> = gson.fromJson(json, type)

            expensesList.addAll(loadedExpenses)
        } catch (e: FileNotFoundException) {
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
