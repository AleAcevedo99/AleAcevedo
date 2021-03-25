package com.example.aleacevedo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
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

        binding.edtDate.setOnClickListener {
            val myCalendar: Calendar
            val y: Int
            val m: Int
            val d: Int
            if(binding.edtDate.text.toString().isEmpty()){
                myCalendar = Calendar.getInstance()
                y = myCalendar.get(Calendar.YEAR)
                m = myCalendar.get(Calendar.MONTH)
                d = myCalendar.get(Calendar.DAY_OF_MONTH)
            }else{
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val myDate: LocalDate? = sdf.parse(binding.edtDate.text.toString())?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate()
                y = myDate!!.year
                m = myDate!!.monthValue-1
                d = myDate!!.dayOfMonth
            }
            val dpd = DatePickerDialog(this@SurveyActivity,
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        binding.edtDate.setText("${twoDigits(dayOfMonth)}/${twoDigits(month+1)}/$year")
                    }, y, m, d)
            dpd.show()
        }


        binding.edtTime.setOnClickListener {
            val myCalendar: Calendar
            val h: Int
            val m: Int
            if(binding.edtTime.text.toString().isEmpty()){
                myCalendar = Calendar.getInstance()
                h = myCalendar.get(Calendar.HOUR_OF_DAY)
                m = myCalendar.get(Calendar.MINUTE)
            }else{
                val stf = SimpleDateFormat("HH:mm")
                val myDate: LocalDateTime? = stf.parse(binding.edtTime.text.toString())?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDateTime()
                h = myDate!!.hour
                m = myDate!!.minute
            }
            val tpd = TimePickerDialog(this@SurveyActivity,
                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                        binding.edtTime.setText("${twoDigits(hourOfDay)}:${twoDigits(minute)}")
                    }, h, m, false)
            tpd.show()
        }

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
            && binding.rgdWriteReviews.checkedRadioButtonId != -1
            && binding.edtTime.text.isNotEmpty()
            && binding.edtDate.text.isNotEmpty()){

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

            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            survey.dateSelected = sdf.parse(binding.edtDate.text.toString())

            val stf = SimpleDateFormat("HH:mm", Locale.getDefault())
            survey.timeSelected = stf.parse(binding.edtTime.text.toString())

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
        binding.edtDate.setText("")
        binding.edtTime.setText("")
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

    fun twoDigits(number:Int):String{
        return if(number <= 9) "0$number" else number.toString()
    }
}