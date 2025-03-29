package com.example.myfirstapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpenseAdapter(
    private val context: Context,
    private val expenses: MutableList<Expense>,
    private val footerFragment: FooterFragment
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

        holder.deleteButton.setOnClickListener {
            val amount = expense.amount
            expenses.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, expenses.size)
            footerFragment.updateTotalAmount(-amount)

            // Save updated list to file
            (context as MainActivity).saveExpensesToFile()
        }

        holder.showDetailsButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, ExpenseDetailsActivity::class.java)
            intent.putExtra("EXPENSE_NAME", expense.name)
            intent.putExtra("EXPENSE_AMOUNT", expense.amount)
            intent.putExtra("EXPENSE_DATE", expense.date)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return expenses.size
    }

    fun addExpense(expense: Expense) {
        expenses.add(expense)
        notifyItemInserted(expenses.size - 1)
        footerFragment.updateTotalAmount(expense.amount)

        // Save updated list to file
        (context as MainActivity).saveExpensesToFile()
    }
}
