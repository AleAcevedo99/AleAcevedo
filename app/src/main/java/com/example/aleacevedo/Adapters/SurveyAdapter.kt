package com.example.aleacevedo.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.aleacevedo.DetailActivity
import com.example.aleacevedo.EditActivity
import com.example.aleacevedo.Entity.EntitySurvey
import com.example.aleacevedo.Entity.ListSurveys
import com.example.aleacevedo.R
import com.example.aleacevedo.Tools.Constants
import com.example.aleacevedo.databinding.ItemSurveyBinding
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SurveyAdapter (var surveysList:ArrayList<EntitySurvey>, val context: Context, val surveys:ListSurveys): RecyclerView.Adapter<SurveytHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurveytHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SurveytHolder(inflater.inflate(R.layout.item_survey, parent, false))
    }

    override fun getItemCount(): Int {
        return surveysList.size
    }

    override fun onBindViewHolder(holder: SurveytHolder, position: Int) {
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val sdt = SimpleDateFormat("HH:mm")

        if(surveysList[position].gender == 1){
            holder.imgLogo.setImageResource(R.drawable.avatar_hombre)
        }else{
            holder.imgLogo.setImageResource(R.drawable.avatar_mujer)
        }
        holder.txvName.text = "${surveysList[position].name}"
        holder.txvDate.text = "${simpleDateFormat.format(surveysList[position].date)}"
        holder.txvReadFrecuency.text = "Lee: ${if (surveysList[position].readFrecuency == 1) "Muy frecuentemente"
        else if(surveysList[position].readFrecuency == 2) "De vez en cuando"
        else if(surveysList[position].readFrecuency == 3) "Poco"
        else "No especificado"}"

        holder.txvSelectedDate.text = "Fecha ingresada: ${sdf.format(surveysList[position].dateSelected)}"
        holder.txvSelectedTime.text = "Tiempo ingresado: ${sdt.format(surveysList[position].timeSelected)}"

        holder.btnDelete.setOnClickListener {
            actionDialog(surveysList[position].name, surveysList[position].user, position, it).show()
        }

        holder.btnEdit.setOnClickListener {
            val intent = Intent(context, EditActivity::class.java).apply{
                putExtra(Constants.SURVEY_NAME, surveysList[position].name)
                putExtra(Constants.USER, surveysList[position].user)
            }
            context.startActivity(intent)
        }

        holder.btnView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java).apply{
                putExtra(Constants.SURVEY_NAME, surveysList[position].name)
                putExtra(Constants.USER, surveysList[position].user)
            }

            context.startActivity(intent)
        }

    }

    fun actionDialog(name:String, user:Int, position: Int, view: View): AlertDialog {
        val alert = AlertDialog.Builder(context)
        alert.setTitle("Ale App")
        alert.setIcon(R.drawable.fenix)
        alert.setMessage("¿Realmente deseas eliminar la encuesta?")

        alert.setPositiveButton("Sí"){_,_ ->
            if(surveys.delete(name, user)){
                Toast.makeText(context, "Encuesta eliminada", Toast.LENGTH_SHORT).show()
                surveysList.removeAt(position)
                //surveysList = surveys.getListArraySurveys(user)
                notifyDataSetChanged()
            }else{
                Snackbar.make(view, "Error al eliminar", Snackbar.LENGTH_SHORT).show()
            }
        }


        alert.setNegativeButton("No"){_,_ ->

        }

        return  alert.create()
    }

}

class SurveytHolder(view: View): RecyclerView.ViewHolder(view){
    val binding = ItemSurveyBinding.bind(view)

    val imgLogo = binding.imvLogo
    val txvName = binding.txvName
    val txvDate = binding.txvDate
    val txvReadFrecuency = binding.txvReadFrecuency
    val txvSelectedDate = binding.txvSelectedDate
    val txvSelectedTime = binding.txvSelectedTime
    val btnDelete = binding.btnDelete
    val btnEdit = binding.btnEdit
    val btnView = binding.btnView

}
