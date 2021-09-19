package com.example.myapplication.network.api.response

data class GitHubRepo(
    val author : String,
    val name : String,
    val avatar : String,
    val url : String,
    val description : String,
    val language : String,
    val languageColor : String,
    val stars : Int,
    val forks : Int,
    val currentPeriodStars : Int,
    val builtBy : List<BuiltBy>
)
