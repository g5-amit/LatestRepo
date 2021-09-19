package com.example.myapplication.ui

import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.navigator.AppNavigator
import com.example.myapplication.navigator.Screens
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Main Activity which is the container for Trending Repo Fragment List
 * */

@AndroidEntryPoint
class RepoActivity : BaseActivity() {
    @Inject
    lateinit var navigator: AppNavigator

    override fun getLayoutRes(): Int {
        return R.layout.repo_activity
    }

    override fun initView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            navigator.navigateTo(Screens.TRENDING_REPO)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        }
    }
}