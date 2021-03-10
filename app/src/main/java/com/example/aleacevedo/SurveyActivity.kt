package com.example.aleacevedo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.aleacevedo.Entity.EntitySurvey
import com.example.aleacevedo.Entity.ListSurveys
import com.example.aleacevedo.Entity.ListUsers
import com.example.aleacevedo.Tools.Constants
import com.example.aleacevedo.databinding.ActivityHomeBinding
import com.example.aleacevedo.databinding.ActivitySurveyBinding
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

class SurveyActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySurveyBinding
    private var listSurveys = ListSurveys()
    private var userPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySurveyBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setTitle(R.string.txt_survey)

        userPosition= intent.getIntExtra(Constants.USER, -1)

        if(userPosition != -1){
            binding.btnOk.setOnClickListener {
                if(binding.edtName.text.trim().isNotEmpty()  && binding.rgdUsesApp.checkedRadioButtonId != -1
                        && binding.spnFrecuency.selectedItemPosition != 0){
                    val survey = EntitySurvey()
                    survey.name = binding.edtName.text.toString()
                    survey.user = userPosition
                    survey.readFrecuency = binding.spnFrecuency.selectedItemPosition

                    when(binding.rgdUsesApp.checkedRadioButtonId){
                        binding.rdbYes.id -> { survey.readApp = true }
                        binding.rdbNo.id -> { survey.readApp = false }
                    }

                    survey.recomendationPerAuthor = binding.ckbPerAuthor.isChecked
                    survey.recomendationsPerGender = binding.ckbPerGender.isChecked
                    survey.recomendationPerTopic = binding.ckbRecPerTopic.isChecked
                    survey.shelfOrganization = binding.ckbShelfOrganization.isChecked
                    survey.interested = binding.swtInterested.isChecked
                    survey.date = Date()

                    val index = listSurveys.add(survey)
                    if(index != -1){
                        Toast.makeText(this@SurveyActivity, "Encuesta registrada", Toast.LENGTH_SHORT).show()
                        cleanForm()
                    }else{
                        Snackbar.make(it, "El nombre de la encuesta no puede repetirse",
                                Snackbar.LENGTH_SHORT).show()
                    }

                }else{
                    Snackbar.make(it, "Todos los campos son obligatorios",
                            Snackbar.LENGTH_SHORT).show()
                }
            }
        }else{
            Toast.makeText(this@SurveyActivity, "Error al cargar la actividad", Toast.LENGTH_SHORT).show()
            finish()
        }



    }

    fun cleanForm(){
        binding.edtName.setText("")
        binding.spnFrecuency.setSelection(0)
        binding.rgdUsesApp.clearCheck()
        binding.ckbPerAuthor.isChecked = false
        binding.ckbPerGender.isChecked = false
        binding.ckbShelfOrganization.isChecked = false
        binding.ckbRecPerTopic.isChecked = false
        binding.swtInterested.isChecked = false
    }
}