package com.example.myapplication.customviews

sealed class FullScreenViewType {
    object LoadingView: FullScreenViewType()
    object ErrorView: FullScreenViewType()
}