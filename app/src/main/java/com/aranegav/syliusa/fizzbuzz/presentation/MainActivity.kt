package com.aranegav.syliusa.fizzbuzz.presentation

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.aranegav.syliusa.fizzbuzz.databinding.MainLayoutBinding

class MainActivity: FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Inflate & Set MainActivity's layout
        val binding = MainLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}