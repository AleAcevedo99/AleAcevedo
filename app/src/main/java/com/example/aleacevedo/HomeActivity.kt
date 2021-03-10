package com.example.aleacevedo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.aleacevedo.Entity.ListSurveys
import com.example.aleacevedo.Entity.ListUsers
import com.example.aleacevedo.Tools.Constants
import com.example.aleacevedo.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private var listSurveys = ListSurveys()
    private var userPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setTitle(R.string.txt_home)

        userPosition= intent.getIntExtra(Constants.USER, -1)
        if(userPosition != -1){
            loadSurveyList()

            binding.ltvSurvey.setOnItemClickListener{ adapterView: AdapterView<*>, view1: View,
                                                        position: Int, id: Long ->
                val selectedItem = adapterView.getItemAtPosition(position).toString()
                val name = selectedItem.split("|")[0].trim()
                actionDialog(position, name).show()

            }

        }else{
            Toast.makeText(this@HomeActivity, "Error al cargar la actividad", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    fun actionDialog(position: Int, name: String): AlertDialog {
        val alert = AlertDialog.Builder(this@HomeActivity)
        alert.setMessage("¿Qué desea hacer?")

        alert.setPositiveButton("Eliminar"){_,_ ->
            if(listSurveys.delete(name, userPosition)){
                Toast.makeText(this@HomeActivity, "Encuesta eliminada", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this@HomeActivity, "Error al eliminar", Toast.LENGTH_SHORT).show()
            }
            loadSurveyList()
        }

        alert.setNegativeButton("Editar"){_,_ ->
            val intent = Intent(this@HomeActivity, EditActivity::class.java).apply{
                putExtra(Constants.SURVEY_NAME, name)
                putExtra(Constants.USER, userPosition)
            }
            startActivity(intent)
        }

        alert.setNeutralButton("Ver"){_,_ ->
            val intent = Intent(this@HomeActivity, DetailActivity::class.java).apply{
                putExtra(Constants.SURVEY_NAME, name)
                putExtra(Constants.USER, userPosition)
            }
            startActivity(intent)
        }


        return  alert.create()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itmCreateSurvey -> {
                val intent = Intent(this@HomeActivity, SurveyActivity::class.java).apply{
                        putExtra(Constants.USER, userPosition)
                    }
                startActivity(intent)
            }
            R.id.itmExit -> {
                    val intent = Intent(this@HomeActivity, LogInActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRestart() {
        super.onRestart()
        userPosition= intent.getIntExtra(Constants.USER, -1)
        if(userPosition != -1){
            loadSurveyList()
        }else{
            Toast.makeText(this@HomeActivity, "Error al cargar la actividad", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    fun loadSurveyList(){
        val adapter = ArrayAdapter<String>(this@HomeActivity,
                android.R.layout.simple_list_item_1, listSurveys.getStringArraySurveys(userPosition))

        binding.ltvSurvey.adapter = adapter

    }
}