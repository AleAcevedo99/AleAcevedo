package com.example.aleacevedo.Entity

import java.util.*

data class EntitySurvey(
    var name: String,
    var user: Int,
    var gender: Int,
    var ageCategory: Int,
    var readFrecuency:Int,
    var readApp:Boolean,
    var recomendationPerAuthor: Boolean,
    var recomendationsPerGender: Boolean,
    var shelfOrganization: Boolean,
    var recomendationPerTopic: Boolean,
    var interested: Boolean,
    var selectPerAuthor: Boolean,
    var selectPerReview:Boolean,
    var selectPerGender: Boolean,
    var disavantage: String,
    var writeReviews: Boolean,
    var date: Date?,
    var dateSelected: Date?,
    var timeSelected: Date?){
    constructor():this("", 0, 0,0, 0,false, false, false, false, false,
    false, false, false, false, "", false, null, null, null)
}