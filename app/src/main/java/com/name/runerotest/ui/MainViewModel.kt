package com.name.runerotest.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application


    fun setNameState(state: String?) {
        val sheared = context.getSharedPreferences("NameState", Context.MODE_PRIVATE)

        sheared.edit().apply {
            putString("NameState", state)
        }.apply()
    }

    fun getNameState(): String? {
        val sheared = context.getSharedPreferences("NameState", Context.MODE_PRIVATE)

        return sheared.getString("NameState", "Капучино эконом")
    }

    fun setPriceState(state: String?) {
        val sheared = context.getSharedPreferences("PriceState", Context.MODE_PRIVATE)

        sheared.edit().apply {
            putString("PriceState", state)
        }.apply()
    }

    fun getPriceState(): String? {
        val sheared = context.getSharedPreferences("PriceState", Context.MODE_PRIVATE)

        return sheared.getString("PriceState", null)
    }

    fun setTypeFull(state: Boolean) {
        val sheared = context.getSharedPreferences("TypeFull", Context.MODE_PRIVATE)

        sheared.edit().apply {
            putBoolean("TypeFull", state)
        }.apply()
    }

    fun getTypeFull(): Boolean {
        val sheared = context.getSharedPreferences("TypeFull", Context.MODE_PRIVATE)

        return sheared.getBoolean("TypeFull", true)
    }



}