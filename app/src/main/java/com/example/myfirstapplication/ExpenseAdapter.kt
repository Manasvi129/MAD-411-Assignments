package com.example.myfirstapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView

class ExpenseAdapter(
    private val expenses: MutableList<Expense>,
    private val navController: NavController,
    private val updateTotalAmount: (Double) -> Unit,
    private val saveExpensesToFile: () -> Unit
) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.expenseNameTextView)
        val amountTextView: TextView = itemView.findViewById(R.id.expenseAmountTextView)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        val showDetailsButton: Button = itemView.findViewById(R.id.showDetailsButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]
        holder.nameTextView.text = expense.name
        holder.amountTextView.text = expense.amount.toString()

        // Delete button functionality
        holder.deleteButton.setOnClickListener {
            val amount = expense.amount
            expenses.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, expenses.size)
            updateTotalAmount(-amount)

            // Save updated list to file
            saveExpensesToFile()
        }

        // Show details button functionality using SafeArgs
        holder.showDetailsButton.setOnClickListener {
            val action = ExpenseListFragmentDirections
                .actionExpenseListToExpenseDetails(
                    expenseName = expense.name,
                    expenseAmount = expense.amount.toFloat(),
                    expenseDate = expense.date
                )
            navController.navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return expenses.size
    }

    fun addExpense(expense: Expense) {
        expenses.add(expense)
        notifyItemInserted(expenses.size - 1)
        updateTotalAmount(expense.amount)

        saveExpensesToFile()
    }
}
