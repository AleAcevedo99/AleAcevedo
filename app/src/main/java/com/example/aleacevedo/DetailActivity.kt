package com.example.aleacevedo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.aleacevedo.Entity.ListSurveys
import com.example.aleacevedo.Tools.Constants
import com.example.aleacevedo.databinding.ActivityDetailBinding
import com.example.aleacevedo.databinding.ActivityHomeBinding
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var listSurveys = ListSurveys()
    private var userPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val name = intent.getStringExtra(Constants.SURVEY_NAME)
        userPosition= intent.getIntExtra(Constants.USER, -1)

        supportActionBar?.setTitle(R.string.txt_detail)

        if(name != null && userPosition != -1){
            var survey = listSurveys.getSurvey(name, userPosition)
            if(survey != null){
                val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm")

                binding.txvName.setText(survey.name)

                binding.txvReadFrecuency.text = "Lee: ${if (survey.readFrecuency == 1) "Muy frecuentemente"
                else if(survey.readFrecuency == 2) "De vez en cuando"
                else if(survey.readFrecuency == 3) "Poco" 
                else "No especificado"}"

                binding.txvReadApp.text = "${if (survey.readApp) "Usa una app de libros"
                else "No usa una app de libros"}"

                binding.txvFeautres.text = "Funcionalidades que le interesan: ${if (survey.recomendationPerAuthor) "Recomendaciones por autor" else ""}" +
                        " ${if (survey.recomendationsPerGender) "Recomendaciones por género" else ""}" +
                        " ${if (survey.shelfOrganization) "Organización de libreros" else ""} " +
                        "${if (survey.recomendationPerTopic) "Recomendaciones por trama" else ""} " +
                        "${if (!survey.recomendationPerAuthor && !survey.recomendationsPerGender
                                && !survey.shelfOrganization && !survey.recomendationPerTopic) "Sin registrar" else ""}"

                binding.txvInterested.text = "${if(survey.interested) "Interesado en la app" else "No interesado en la app"}"

                binding.txvDate.text = "Fecha: ${simpleDateFormat.format(survey.date)}"

            }else{
                Toast.makeText(this@DetailActivity, "Error al cargar la actividad", Toast.LENGTH_SHORT).show()
                finish()
            }
        }else{
            Toast.makeText(this@DetailActivity, "Error al cargar la actividad", Toast.LENGTH_SHORT).show()
            finish()
        }

    }
}