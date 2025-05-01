package com.example.myfirstapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ExpenseDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_details)

        // Get the data from the Intent
        val expenseName = intent.getStringExtra("EXPENSE_NAME")
        val expenseAmount = intent.getDoubleExtra("EXPENSE_AMOUNT", 0.0)
        val expenseDate = intent.getStringExtra("EXPENSE_DATE")

        // Find TextViews in  layout
        val nameTextView = findViewById<TextView>(R.id.detailNameTextView)
        val amountTextView = findViewById<TextView>(R.id.detailAmountTextView)
        val dateTextView = findViewById<TextView>(R.id.detailDateTextView)

        // Set the data to the expense textview
        nameTextView.text = "Name: $expenseName"
        amountTextView.text = "Amount: $expenseAmount"
        dateTextView.text = "Date: $expenseDate"
    }
}
