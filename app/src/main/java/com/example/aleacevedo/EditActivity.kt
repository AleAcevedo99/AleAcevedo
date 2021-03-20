package com.example.aleacevedo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.aleacevedo.Entity.EntitySurvey
import com.example.aleacevedo.Entity.ListSurveys
import com.example.aleacevedo.Tools.Constants
import com.example.aleacevedo.databinding.ActivityDetailBinding
import com.example.aleacevedo.databinding.ActivityEditBinding
import com.example.aleacevedo.databinding.ActivityHomeBinding
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    private var listSurveys = ListSurveys()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEditBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setTitle(R.string.txt_edit)

        val name = intent.getStringExtra(Constants.SURVEY_NAME)
        val userPosition = intent.getIntExtra(Constants.USER, -1)

        if(name != null && userPosition != -1){
            var surveyOrigin = listSurveys.getSurvey(name, userPosition)
            if(surveyOrigin != null){

                binding.edtName.setText(surveyOrigin.name)

                when(surveyOrigin.gender){
                    1->{ binding.rgdGender.check(binding.rdbMen.id) }
                    0->{ binding.rgdGender.check(binding.rdbWoman.id) }
                }

                binding.spnFrecuency.setSelection(surveyOrigin.readFrecuency)

                when(surveyOrigin.readApp){
                    true->{ binding.rgdUsesApp.check(binding.rdbYes.id) }
                    false->{ binding.rgdUsesApp.check(binding.rdbNo.id) }
                }

                binding.ckbPerAuthor.isChecked = surveyOrigin.recomendationPerAuthor
                binding.ckbPerGender.isChecked = surveyOrigin.recomendationsPerGender
                binding.ckbShelfOrganization.isChecked = surveyOrigin.shelfOrganization
                binding.ckbRecPerTopic.isChecked = surveyOrigin.recomendationPerTopic
                binding.swtInterested.isChecked = surveyOrigin.interested

                binding.spnAgeCategory.setSelection(surveyOrigin.ageCategory)

                binding.edtDisavantage.setText(surveyOrigin.disavantage)

                when(surveyOrigin.writeReviews){
                    true->{ binding.rgdWriteReviews.check(binding.rdbYesReviews.id) }
                    false->{ binding.rgdWriteReviews.check(binding.rdbNoReviews.id) }
                }

                binding.ckbSelectPerAuthor.isChecked = surveyOrigin.selectPerAuthor
                binding.ckbSelectPerGender.isChecked = surveyOrigin.selectPerGender
                binding.ckbSelectPerReview.isChecked = surveyOrigin.selectPerReview

                binding.btnOk.setOnClickListener{
                    if(binding.edtName.text.trim().isNotEmpty()
                            && binding.rgdGender.checkedRadioButtonId != -1
                            && binding.rgdUsesApp.checkedRadioButtonId != -1
                            && binding.spnFrecuency.selectedItemPosition != 0
                            && binding.spnAgeCategory.selectedItemPosition != 0
                            && binding.edtDisavantage.text.trim().isNotEmpty()
                            && binding.rgdWriteReviews.checkedRadioButtonId != -1){

                        val survey = EntitySurvey()
                        survey.name = binding.edtName.text.toString()
                        survey.user = userPosition

                        when(binding.rgdGender.checkedRadioButtonId){
                            binding.rdbMen.id -> { survey.gender = 1 }
                            binding.rdbWoman.id -> { survey.gender = 0 }
                        }

                        survey.readFrecuency = binding.spnFrecuency.selectedItemPosition

                        when(binding.rgdUsesApp.checkedRadioButtonId){
                            binding.rdbYes.id -> { survey.readApp = true }
                            binding.rdbNo.id -> { survey.readApp = false }
                        }
                        survey.ageCategory = binding.spnAgeCategory.selectedItemPosition

                        survey.disavantage = binding.edtDisavantage.text.toString()

                        when(binding.rgdWriteReviews.checkedRadioButtonId){
                            binding.rdbNoReviews.id -> { survey.writeReviews = false }
                            binding.rdbYesReviews.id -> { survey.writeReviews = true }
                        }

                        survey.recomendationPerAuthor = binding.ckbPerAuthor.isChecked
                        survey.recomendationsPerGender = binding.ckbPerGender.isChecked
                        survey.recomendationPerTopic = binding.ckbRecPerTopic.isChecked
                        survey.shelfOrganization = binding.ckbShelfOrganization.isChecked
                        survey.interested = binding.swtInterested.isChecked

                        survey.selectPerAuthor = binding.ckbSelectPerAuthor.isChecked
                        survey.selectPerGender = binding.ckbSelectPerGender.isChecked
                        survey.selectPerReview = binding.ckbSelectPerReview.isChecked

                        survey.date = Date()

                        val index = listSurveys.edit(survey, surveyOrigin.name, userPosition)
                        if(index != -1){
                            Toast.makeText(this@EditActivity, "Encuesta actualizada", Toast.LENGTH_SHORT).show()
                            cleanForm()
                            val intent = Intent(this@EditActivity, HomeActivity::class.java).apply{
                                putExtra(Constants.USER, userPosition)
                                finish()
                            }
                            startActivity(intent)

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
                Toast.makeText(this@EditActivity, "Error al cargar la actividad", Toast.LENGTH_SHORT).show()
                finish()
            }
        }else{
            Toast.makeText(this@EditActivity, "Error al cargar la actividad", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    fun cleanForm(){
        binding.edtName.setText("")
        binding.rgdGender.clearCheck()
        binding.spnFrecuency.setSelection(0)
        binding.rgdUsesApp.clearCheck()
        binding.spnAgeCategory.setSelection(0)
        binding.ckbPerAuthor.isChecked = false
        binding.ckbPerGender.isChecked = false
        binding.ckbShelfOrganization.isChecked = false
        binding.ckbRecPerTopic.isChecked = false
        binding.swtInterested.isChecked = false
        binding.ckbSelectPerAuthor.isChecked = false
        binding.ckbSelectPerGender.isChecked = false
        binding.ckbSelectPerReview.isChecked = false
        binding.edtDisavantage.setText("")
        binding.rgdWriteReviews.clearCheck()
    }
}