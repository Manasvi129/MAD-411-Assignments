package com.example.myfirstapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class FooterFragment : Fragment() {

    private lateinit var totalAmountTextView: TextView
    private var totalAmount: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_footer, container, false)

        totalAmountTextView = view.findViewById(R.id.totalAmountTextView)
        updateTotalAmountText()

        return view
    }

    fun updateTotalAmount(amount: Double) {
        totalAmount += amount
        updateTotalAmountText()
    }

    private fun updateTotalAmountText() {
        totalAmountTextView.text = "Total Expenses is : $totalAmount"
    }


}
