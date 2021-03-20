package com.example.aleacevedo.Tools

class Constants {

    companion object{
        const val LOG_TAG = "UdelP"
        const val USER = "User"
        const val INDEX = "Index"
        const val SURVEY_NAME = "SurveyName"
        val ACCESS_FINE_LOCATION = arrayOf<String>(android.Manifest.permission.ACCESS_FINE_LOCATION)
        val RECORD_AUDIO = arrayOf<String>(android.Manifest.permission.RECORD_AUDIO)
        val WRITE_EXTERNAL_STORAGE = arrayOf<String>(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val LIST_PERMISSIONS = arrayOf<String>(android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.RECORD_AUDIO,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
}