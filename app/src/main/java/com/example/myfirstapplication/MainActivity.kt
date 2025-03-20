package com.example.myfirstapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var expenseNameEditText: EditText
    private lateinit var amountEditText: EditText
    private lateinit var dateEditText: EditText
    private lateinit var addExpenseButton: Button
    private lateinit var expensesRecyclerView: RecyclerView
    private lateinit var expenseAdapter: ExpenseAdapter
    private val expensesList = ArrayList<Expense>()
    private lateinit var footerFragment: FooterFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("MainActivityLifecycle", "onCreate called")

        // Initialize new UI for Expense Tracker application
        expenseNameEditText = findViewById(R.id.expenseNameEditText)
        amountEditText = findViewById(R.id.amountEditText)
        dateEditText = findViewById(R.id.dateEditText)
        addExpenseButton = findViewById(R.id.addExpenseButton)
        expensesRecyclerView = findViewById(R.id.expensesRecyclerView)



        // Add Header and Footer Fragments dynamically
        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        val headerFragment = HeaderFragment()
        footerFragment = FooterFragment() // Initialize footerFragment

        transaction.add(R.id.headerContainer, headerFragment)
        transaction.add(R.id.footerContainer, footerFragment)
        transaction.commit()

        // RecyclerView
        expensesRecyclerView.layoutManager = LinearLayoutManager(this)
        expenseAdapter = ExpenseAdapter(expensesList, footerFragment)
        expensesRecyclerView.adapter = expenseAdapter

        addExpenseButton.setOnClickListener {
            addExpenseClick()
        }

    }


    //added log
    override fun onStart() {
        super.onStart()
        Log.d("MainActivityLifecycle", "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivityLifecycle", "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivityLifecycle", "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivityLifecycle", "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivityLifecycle", "onDestroy called")
    }



    private fun addExpenseClick() {
        val name = expenseNameEditText.text.toString()
        val amountString = amountEditText.text.toString()
        val date = dateEditText.text.toString()

        // Convert amount to Double
        val amount = amountString.toDoubleOrNull() ?: 0.0
        val expense = Expense(name, amount, date)

        expenseAdapter.addExpense(expense)

        // Clear the input fields for the next entery
        expenseNameEditText.text.clear()
        amountEditText.text.clear()
        dateEditText.text.clear()
    }

    private fun openFinancialTips() {
        val url = "https://www.investopedia.com"
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

}
