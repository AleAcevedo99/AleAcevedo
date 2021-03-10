package com.example.aleacevedo.Entity

import java.util.*

data class EntitySurvey(
    var name: String,
    var user: Int,
    var readFrecuency:Int,
    var readApp:Boolean,
    var recomendationPerAuthor: Boolean,
    var recomendationsPerGender: Boolean,
    var shelfOrganization: Boolean,
    var recomendationPerTopic: Boolean,
    var interested: Boolean,
    var date: Date?){
    constructor():this("", 0, 0, false, false, false, false, false,
    false, null)
}