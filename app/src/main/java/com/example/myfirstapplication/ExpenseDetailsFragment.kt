package com.example.myfirstapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class ExpenseDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_expense_details, container, false)

        // Retrieve data from the arguments
        val expenseName = arguments?.getString("EXPENSE_NAME") ?: "No Name"
        val expenseAmount = arguments?.getDouble("EXPENSE_AMOUNT")?: "No Amount"
        val expenseDate = arguments?.getString("EXPENSE_DATE") ?: "No Date"

        // Set data to the TextViews
        view.findViewById<TextView>(R.id.detailNameTextView).text = expenseName
        view.findViewById<TextView>(R.id.detailAmountTextView).text = expenseAmount .toString()
        view.findViewById<TextView>(R.id.detailDateTextView).text = expenseDate

        return view
    }
}
