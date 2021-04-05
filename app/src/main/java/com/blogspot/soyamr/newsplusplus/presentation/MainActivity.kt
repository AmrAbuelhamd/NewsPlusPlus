package com.blogspot.soyamr.newsplusplus.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blogspot.soyamr.newsplusplus.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_NewsPlusPlus);
        setContentView(R.layout.activity_main)
    }
}