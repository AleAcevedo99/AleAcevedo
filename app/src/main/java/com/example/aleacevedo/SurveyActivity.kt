package com.example.aleacevedo

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.aleacevedo.Entity.EntitySurvey
import com.example.aleacevedo.Entity.ListSurveys
import com.example.aleacevedo.Entity.ListUsers
import com.example.aleacevedo.Tools.ApplicationPermissions
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
    private val permission = ApplicationPermissions(this@SurveyActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySurveyBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setTitle(R.string.txt_survey)

        userPosition= intent.getIntExtra(Constants.USER, -1)

        if(userPosition != -1){
            binding.btnOk.setOnClickListener {
                if(!permission.hasPermissions(Constants.WRITE_EXTERNAL_STORAGE)){
                    permission.acceptPermission(Constants.WRITE_EXTERNAL_STORAGE, 3)
                }else{
                    saveSurvey()
                }

            }
        }else{
            Toast.makeText(this@SurveyActivity, "Error al cargar la actividad", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    fun saveSurvey(){
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

            val index = listSurveys.add(survey)
            if(index != -1){
                Toast.makeText(this@SurveyActivity, "Encuesta registrada", Toast.LENGTH_SHORT).show()
                cleanForm()
            }else{
                Snackbar.make(findViewById(android.R.id.content), "El nombre de la encuesta no puede repetirse",
                    Snackbar.LENGTH_SHORT).show()
            }

        }else{
            Snackbar.make(findViewById(android.R.id.content), "Todos los campos son obligatorios",
                Snackbar.LENGTH_SHORT).show()
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            3 -> {
                if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    actionDialog("Es obligatorio aceptar el permiso de escribir en la memoria para realizar esta acción").show()
                }else{
                    saveSurvey()
                }

            }

        }
    }

    fun actionDialog(message:String): AlertDialog {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Ale App")
        alert.setIcon(R.drawable.fenix)
        alert.setMessage(message)
        alert.setPositiveButton("Ok"){_,_ ->

        }
        return  alert.create()
    }
}