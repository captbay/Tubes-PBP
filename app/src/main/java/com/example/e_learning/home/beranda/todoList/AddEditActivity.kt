package com.example.e_learning.home.beranda.todoList


//import com.example.e_learning.databinding.ActivityAuthBinding
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.e_learning.R
import com.example.e_learning.databinding.ActivityAddEditBinding
import com.google.gson.Gson
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.shashank.sony.fancytoastlib.FancyToast
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.time.LocalDate


class AddEditActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddEditBinding
    private var queue: RequestQueue? = null
    private var layoutLoading: LinearLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditBinding.inflate(layoutInflater)
        val view =binding.root
        setContentView(view)
        Logger.addLogAdapter(AndroidLogAdapter())

        queue = Volley.newRequestQueue(this)
        layoutLoading = findViewById(R.id.layout_loading)
        val sharedPreferences = getSharedPreferences("user", 0)
        val user_id = sharedPreferences.getInt("id", 0)
        Log.d("User ID : " , user_id.toString())
        val id = intent.getLongExtra("id", -1)
        if(id==-1L)
        {
            binding.tvJudulAddedit.setText("Tambah Todo")
            binding.btnSave.setOnClickListener{ createTodo(user_id)}
        }else
        {
            binding.tvJudulAddedit.setText("Edit Todo")
            Logger.d("ini_id",id.toString())
            getTodoById(id)
            binding.btnSave.setOnClickListener { updateTodo(id, user_id) }
        }
    }

    private fun createTodo(user_id : Int)
    {
//        setLoading(true)
        val current = LocalDate.now().toString()
        if(binding.etTodo?.getText().toString().isEmpty()){
            FancyToast.makeText(this@AddEditActivity,"Judul tidak boleh kosong", FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
        }else if(binding.etPesan?.getText().toString().isEmpty()){
            FancyToast.makeText(this@AddEditActivity,"Pesan tidak boleh kosong", FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
        }else if(binding.etDibuat?.getText().toString().isEmpty()){
            FancyToast.makeText(this@AddEditActivity,"Tanggal Dibuat tidak boleh kosong", FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
        }else if(binding.etDeadline?.getText().toString().isEmpty()){
            FancyToast.makeText(this@AddEditActivity,"Tanggal Deadline tidak boleh kosong", FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
        }else{
            var judul = binding.etTodo?.getText().toString()
            var pesan = binding.etPesan?.getText().toString()
            var dibuat = binding.etDibuat?.getText().toString()
            var deadline = binding.etDeadline?.getText().toString()
            var userID = user_id
//            Logger.d("Ini user idnya : "+ user_id)
//            Logger.d("inputan" , judul + pesan + dibuat +deadline)
            val todoo = ToDoList( judul, pesan, dibuat, deadline,0, userID)
            Logger.d("Ini  objek todonya", ToDoList::class.java)
            Log.d("initodo",todoo.pesan + todoo.tglDeadline + current + todoo.user_id)
            val stringRequest: StringRequest =
                object : StringRequest(Request.Method.POST,
                    TodoApi.ADD_URL, Response.Listener { response ->
                        Logger.d("iniresponse",response)
                        val gson = Gson()
                        var todoo = gson.fromJson(response, ToDoList::class.java)

                        if(todoo != null)
                            FancyToast.makeText(this@AddEditActivity,"Data Berhasil Ditambah",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();

                        val returnIntent = Intent()
                        setResult(RESULT_OK, returnIntent)
                        finish()

                        setLoading(false)
                    }, Response.ErrorListener { error ->
                        setLoading(false)
                        Logger.d("responseror",error.toString())
                        try{
                            val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                            val errors = JSONObject(responseBody)
                            FancyToast.makeText(this@AddEditActivity,errors.getString("message"),FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show();
                        } catch (e: Exception) {
                            FancyToast.makeText(this@AddEditActivity,e.message,FancyToast.LENGTH_SHORT, FancyToast.WARNING,false).show();
                        }
                    }) {
                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Accept"] = "application/json"
                        return headers
                    }

                    @Throws(AuthFailureError::class)
                    override fun getBody(): ByteArray {
                        val gson = Gson()
                        val requestBody = gson.toJson(todoo)
                        return requestBody.toByteArray(StandardCharsets.UTF_8)
                    }

                    override fun getBodyContentType(): String {
                        return "application/json"
                    }
                }
            // Menambahkan request ke request queue
            queue!!.add(stringRequest)
        }

    }

    private fun updateTodo(id : Long, user_id : Int) {
        setLoading(true)
        val current = LocalDate.now()
        val Todo = ToDoList(
            binding.etTodo.getText().toString(),
            binding.etPesan.getText().toString(),
            binding.etDibuat.getText().toString(),
            binding.etDeadline.getText().toString(),
            0, user_id
        )

        val stringRequest: StringRequest = object :
            StringRequest(Method.PUT, TodoApi.UPDATE_URL + id, Response.Listener { response ->
                val gson = Gson()
                var todo = gson.fromJson(response, Todo::class.java)

                if (todo != null)
                    FancyToast.makeText(this,"Data Berhasil Diupdate",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();

                val returnIntent = Intent()
                setResult(RESULT_OK, returnIntent)
                finish()

                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    FancyToast.makeText(this@AddEditActivity, errors.getString("message"), FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show();
                } catch (e: Exception) {
                    FancyToast.makeText(this@AddEditActivity,e.message,FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show();
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                val gson = Gson()
                val requestBody = gson.toJson(Todo)
                return requestBody.toByteArray(StandardCharsets.UTF_8)
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }
        }
        queue!!.add(stringRequest)
    }
    private fun getTodoById(id : Long)
    {
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, TodoApi.GET_BY_ID_URL + id, Response.Listener { response ->
                Logger.d("iniresponeget", response)
                val gson = Gson()
                val todoo = gson.fromJson(response, Array<ToDoList>::class.java)
//
                binding.etTodo!!.setText(todoo[0].judul)
                binding.etPesan!!.setText(todoo[0].pesan)
                binding.etDibuat!!.setText(todoo[0].tglDibuat)
                binding.etDeadline!!.setText(todoo[0].tglDeadline)



                FancyToast.makeText(this@AddEditActivity,"Data berhasil diambil!",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    FancyToast.makeText(this@AddEditActivity, errors.getString("message"), FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show();
                } catch (e: Exception) {
                    FancyToast.makeText(this@AddEditActivity, e.message, FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show();
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }
        queue!!.add(stringRequest)
    }


    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            layoutLoading!!.visibility = View.VISIBLE
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            layoutLoading!!.visibility = View.GONE
        }
    }

}