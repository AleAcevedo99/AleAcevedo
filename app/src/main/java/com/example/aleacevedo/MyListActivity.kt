package com.example.aleacevedo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aleacevedo.Adapters.SurveyAdapter
import com.example.aleacevedo.Entity.ListSurveys
import com.example.aleacevedo.Tools.Constants
import com.example.aleacevedo.databinding.ActivityMyListBinding
import com.google.android.material.snackbar.Snackbar

class MyListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyListBinding
    private var listSurveys = ListSurveys()
    private var userPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMyListBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setTitle(R.string.txt_my_list)

        userPosition= intent.getIntExtra(Constants.USER, -1)
        if(userPosition != -1){
            loadSurveyList()
        }else{
            Snackbar.make(findViewById(R.id.content), "Error al cargar la actividad", Snackbar.LENGTH_SHORT).show()
            finish()
        }

    }

    override fun onRestart() {
        super.onRestart()
        loadSurveyList()
    }

    fun loadSurveyList(){
        var list = listSurveys.getListArraySurveys(userPosition)

        val adapter = SurveyAdapter(list, this@MyListActivity, listSurveys)

        val linearLayout = LinearLayoutManager(this@MyListActivity, LinearLayoutManager.VERTICAL,
                false)
        binding.rwsSurveys.layoutManager = linearLayout
        binding.rwsSurveys.adapter = adapter
    }
}