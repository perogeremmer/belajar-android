package com.example.belajartodolistketiga

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.belajartodolistketiga.api.RetrofitHelper
import com.example.belajartodolistketiga.api.TodoApi
import com.example.belajartodolistketiga.api.TodoData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateTodo : AppCompatActivity() {

    lateinit var btnSubmit : Button
    lateinit var etTitle : EditText
    lateinit var etDescription : EditText

    val apiKey = "<apikey>"
    val token = "Bearer $apiKey"

    val todoApi = RetrofitHelper.getInstance().create(TodoApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_todo)

        etTitle = findViewById(R.id.et_title_create_todo)
        etDescription = findViewById(R.id.et_description_create_todo)
        btnSubmit = findViewById(R.id.btn_submit_create_todo)

        btnSubmit.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {

                val data = TodoData(title = etTitle.text.toString(), description = etDescription.text.toString())
                val response = todoApi.create(token = token, apiKey = apiKey, todoData = data)

                Toast.makeText(
                    applicationContext,
                    "Berhasil membuat Todo!",
                    Toast.LENGTH_SHORT
                ).show()

                finish()
            }
        }
    }
}