package com.example.myapplication.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.myapplication.databinding.FullScreenViewBinding
import com.example.myapplication.customviews.FullScreenViewType.LoadingView
import com.example.myapplication.customviews.FullScreenViewType.ErrorView
import com.example.myapplication.extensions.gone
import com.example.myapplication.extensions.isGone
import com.example.myapplication.extensions.isVisible
import com.example.myapplication.extensions.visible

class FullScreenView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var binding : FullScreenViewBinding = FullScreenViewBinding.inflate(
        LayoutInflater.from(context), this, true)

    fun show(type: FullScreenViewType) {
        when(type) {
            LoadingView -> {
                if(binding.loader.isGone()){
                    binding.loader.visible()
                }
            }
            ErrorView -> {
                if(binding.loader.isVisible()){
                    binding.loader.gone()
                }
                if(binding.errorView.isGone()){
                    binding.errorView.visible()
                }
                if(binding.errorText.isGone()){
                    binding.errorText.visible()
                }
            }
        }
    }

    fun hide(type: FullScreenViewType) {
        when(type) {
            LoadingView -> {
                if(binding.loader.isVisible()){
                    binding.loader.gone()
                }
            }
            ErrorView -> {
                if(binding.errorView.isVisible()){
                    binding.errorView.gone()
                }

                if(binding.errorText.isVisible()){
                    binding.errorText.gone()
                }
            }
        }
    }

}