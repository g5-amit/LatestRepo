package com.example.myapplication.ui.model

import com.example.myapplication.network.api.response.BuiltBy

data class RepoItemUIModel(
    var author: String?,
    var name: String?,
    var avatar: String?,
    var description: String?,
    var stars: Int?,
    var forks: Int?,
    var currentPeriodStars: Int?,
    var language: String?,
    var languageColor: String?,
    var url: String,
    var builtBy: List<BuiltBy>?,
    var timeStamp: Long?
) {
    var isExpanded: Boolean = false
}

