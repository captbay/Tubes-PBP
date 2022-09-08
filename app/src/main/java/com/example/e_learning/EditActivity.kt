package com.example.e_learning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import com.example.e_learning.data.ELEARNINGDB
import com.example.e_learning.data.todoList.Constant
import com.example.e_learning.data.todoList.ToDoList
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.adapter_todo.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {
    val db by lazy { ELEARNINGDB(this) }
    private var todoId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setupView()
        setupListener()
//
// Toast.makeText(this,
//        noteId.toString(),Toast.LENGTH_SHORT).show()
    }
    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType){
            Constant.TYPE_CREATE -> {
                button_update.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                button_save.visibility = View.GONE
                button_update.visibility = View.GONE
                getNote()
            }
            Constant.TYPE_UPDATE -> {
                button_save.visibility = View.GONE
                getNote()
            }
        }
    }
    private fun setupListener() {
        button_save.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.ToDoListDAO().addTodo(
                    ToDoList(
                        0,"","", edit_title.text.toString(),
                        edit_pesan.text.toString(),true
                    )
                )
                finish()
            }
        }

        button_update.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                ToDoList(
                    0,"","", edit_title.text.toString(),
                    edit_pesan.text.toString(),true
                )

                finish()
            }
        }
    }
    fun getNote() {
        todoId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val toDoList = db.ToDoListDAO().getNote(todoId)[0]
            edit_title.setText(toDoList.judul)
            edit_pesan.setText(toDoList.pesan)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}