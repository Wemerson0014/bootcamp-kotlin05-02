package com.estudo.drinkwater

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.ImageView
import android.widget.TextView
import com.estudo.drinkwater.sync.DrinkWaterReminderIntentService
import com.estudo.drinkwater.sync.DrinkWaterReminderTask
import com.estudo.drinkwater.utils.PreferencesUtils

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        updateWaterCount()

        val imageCupIcon = findViewById<ImageView>(R.id.image_cup_icon)
        imageCupIcon.setOnClickListener {
            incrementWaterHandler()
        }

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        prefs.registerOnSharedPreferenceChangeListener(this)

    }

    private fun updateWaterCount() {
        val textQuantify = findViewById<TextView>(R.id.text_quantify)
        val count = PreferencesUtils.getWaterCount(this)
        textQuantify.text = "$count"
    }

    private fun incrementWaterHandler() {
        val intent = Intent(this, DrinkWaterReminderIntentService::class.java)
        intent.action = DrinkWaterReminderTask.ACTION_INCREMENT_WATER_COUNT
        startService(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        prefs.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (PreferencesUtils.KEY_WATER_COUNT == key) {
            updateWaterCount()
        }
    }
}