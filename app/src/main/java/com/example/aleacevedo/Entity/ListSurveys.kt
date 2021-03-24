package com.example.aleacevedo.Entity

import android.provider.SyncStateContract
import android.util.Log
import com.example.aleacevedo.Tools.Constants
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ListSurveys {

    fun add(survey: EntitySurvey): Int{
        var answer: Int = -1
        if(!existsName(survey.name, survey.user)){
            listSurveys.add(survey)
            answer = listSurveys.size - 1
        }
        return answer
    }

    fun existsName(name: String, user:Int):Boolean{
        return listSurveys.filter { it.name == name && it.user == user}.isNotEmpty()
    }

    fun edit(surveyUpdate: EntitySurvey, originName: String, userPosition: Int): Int{
        var answer: Int = -1
        if(surveyUpdate.name == originName || !existsName(surveyUpdate.name, surveyUpdate.user)){
            for((index, item) in listSurveys.withIndex()) {
                if(item.name == originName && item.user == userPosition){
                    listSurveys[index] = surveyUpdate
                    answer = 1
                    break
                }
            }
        }
        return  answer
    }

    fun delete(name: String, userPosition: Int): Boolean{
        var answer: Boolean = false
        for((index, item) in listSurveys.withIndex()) {
            if(item.name == name && item.user == userPosition){
                listSurveys.removeAt(index)
                answer = true
                break
            }
        }
        return  answer
    }

    fun getStringArraySurveys(userPoistion:Int):Array<String>{
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
        val answerList = arrayListOf<String>()
        for((index, item) in listSurveys.withIndex()){
            if(item.user == userPoistion){
                answerList.add("${item.name} | ${simpleDateFormat.format(item.date)}")
            }
        }
        return answerList.toTypedArray()
    }

    fun getListArraySurveys(userPoistion:Int):ArrayList<EntitySurvey>{
        val answerList = arrayListOf<EntitySurvey>()
        for((index, item) in listSurveys.withIndex()){
            if(item.user == userPoistion){
                answerList.add(item)
            }
        }
        return answerList
    }

    fun getSurvey(name:String, userPosition:Int):EntitySurvey?{
        var survey: EntitySurvey? = null
        for(element in listSurveys) {
            if(element.name == name && element.user == userPosition){
                survey = element
                break
            }
        }
        return  survey
    }

    companion object{
        private var listSurveys = arrayListOf<EntitySurvey>()
    }
}