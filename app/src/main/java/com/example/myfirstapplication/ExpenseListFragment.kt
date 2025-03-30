package com.example.myfirstapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

private const val FILE_NAME = "expenses.txt"

class ExpenseListFragment : Fragment(){

    private lateinit var expenseNameEditText: EditText
    private lateinit var amountEditText: EditText
    private lateinit var dateEditText: EditText
    private lateinit var addExpenseButton: Button
    private lateinit var expensesRecyclerView: RecyclerView
    private lateinit var expenseAdapter: ExpenseAdapter
    private val expensesList = ArrayList<Expense>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_expense_list, container, false)

        // Initialize UI components from layout
        expenseNameEditText = view.findViewById(R.id.expenseNameEditText)
        amountEditText = view.findViewById(R.id.amountEditText)
        dateEditText = view.findViewById(R.id.dateEditText)
        addExpenseButton = view.findViewById(R.id.addExpenseButton)
        expensesRecyclerView = view.findViewById(R.id.expensesRecyclerView)



        // Load expenses from file into the list
        loadExpensesFromFile()

        // Set up RecyclerView with adapter and layout manager
        expensesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        expenseAdapter = ExpenseAdapter(expensesList, findNavController(), { amount -> updateTotalAmount(amount) }, { saveExpensesToFile() })
        expensesRecyclerView.adapter = expenseAdapter

        // Set up button click listener for adding a new expense
        addExpenseButton.setOnClickListener {
            addExpenseClick()
        }
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Bundle>("newExpense")
            ?.observe(viewLifecycleOwner) { bundle ->
                val updatedExpense = Expense(
                    bundle.getString("expenseName", ""),
                    bundle.getDouble("expenseAmount", 0.0),
                    bundle.getString("expenseDate", "")
                )
                expensesList.add(updatedExpense)
                expenseAdapter.notifyItemInserted(expensesList.size - 1)
                saveExpensesToFile()
            }

        return view
    }

    private fun addExpenseClick() {
        // Get input values from EditTexts
        val name = expenseNameEditText.text.toString()
        val amountString = amountEditText.text.toString()
        val date = dateEditText.text.toString()

        // Validate inputs (ensure no empty fields)
        if (name.isBlank() || amountString.isBlank() || date.isBlank()) {
            return
        }

        // Convert amount to Double and validate it
        val amount = amountString.toDoubleOrNull() ?: return

        val expense = Expense(name, amount, date)
        expenseAdapter.addExpense(expense)

        // Clear input fields after adding the expense
        expenseNameEditText.text.clear()
        amountEditText.text.clear()
        dateEditText.text.clear()
    }

    private fun updateTotalAmount(amount: Double) {
    }

    private fun saveExpensesToFile() {
        try {
            val gson = Gson()
            val json = gson.toJson(expensesList)
            requireContext().openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use { output ->
                output.write(json.toByteArray())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun loadExpensesFromFile() {
        try {
            val file = File(requireContext().filesDir, FILE_NAME)
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
