package com.example.myfirstapplication

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var EditNameTextView: EditText
    private lateinit var resultnameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        EditNameTextView = findViewById(R.id.nameEditText)
        resultnameTextView = findViewById(R.id.displayNameTextView)
    }

    fun showNameClick(view: View) {
        val username = EditNameTextView.text.toString()
        resultnameTextView.text = "Hello, $username!"
        }
    }
