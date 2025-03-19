package com.example.myfirstapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Implements the ViewHolder pattern for efficient view recycling
class ExpenseAdapter(private val expenses: MutableList<Expense>) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    // ExpenseViewHolder
    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.expenseNameTextView)
        val amountTextView: TextView = itemView.findViewById(R.id.expenseAmountTextView)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }

    // inflates the layout for each expense item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expense, parent, false)
        return ExpenseViewHolder(view)
    }
     //binds the data to view
    override fun onBindViewHolder(expenseViewHolder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]
        expenseViewHolder.nameTextView.text = expense.name
        expenseViewHolder.amountTextView.text = expense.amount.toString()

        //delete button
        expenseViewHolder.deleteButton.setOnClickListener {
            expenses.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, expenses.size)
        }
    }

    //return the result item
    override fun getItemCount(): Int {
        return expenses.size
    }
}
