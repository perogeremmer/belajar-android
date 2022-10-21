package com.example.belajartodolistketiga

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.belajartodolistketiga.api.RetrofitHelper
import com.example.belajartodolistketiga.api.TodoApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SecondActivity : AppCompatActivity() {
    lateinit var labelHeader : TextView
    lateinit var listTodo : ListView

    val apiKey = "<apikey>"
    val token = "Bearer $apiKey"

    var Items = ArrayList<Model>()
    val todoApi = RetrofitHelper.getInstance().create(TodoApi::class.java)

    lateinit var btnCreateTodo : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        labelHeader = findViewById(R.id.label_header)
        listTodo = findViewById(R.id.list_todo)
        btnCreateTodo = findViewById(R.id.btn_create_todo_activity_second)

        val result = intent.getStringExtra("result")
        labelHeader.text = "What's up, $result?"

        getItem()

        listTodo.setOnItemClickListener { adapterView, view, position, id ->
            val item = adapterView.getItemAtPosition(position) as Model
            val title = item.Title

            Toast.makeText(
                applicationContext,
                "Kamu memilih $title",
                Toast.LENGTH_SHORT
            ).show()
        }

        btnCreateTodo.setOnClickListener {
            val intent = Intent(this, CreateTodo::class.java)
            startActivity(intent)
        }
    }

    fun setList(Items: ArrayList<Model>) {
        val adapter = TodoAdapter(this, R.layout.todo_item, Items)
        listTodo.adapter = adapter
    }

    fun getItem() {
        CoroutineScope(Dispatchers.Main).launch {
            val response = todoApi.get(token=token, apiKey=apiKey)
            Items = ArrayList<Model>()

            response.body()?.forEach {
                Items.add(
                    Model(
                        Id=it.id,
                        Title=it.title,
                        Description=it.description
                    )
                )
            }

            setList(Items)
        }
    }

    override fun onResume() {
        super.onResume()
        getItem()
    }
}