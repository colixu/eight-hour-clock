package com.example.eighthourclock

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SettingsActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        title = getString(R.string.settings_title)

        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val editHours = findViewById<EditText>(R.id.edit_hours)
        val editCount = findViewById<EditText>(R.id.edit_count)
        val editInterval = findViewById<EditText>(R.id.edit_interval)

        editHours.setText(prefs.getInt("base_hours", 8).toString())
        editCount.setText(prefs.getInt("alarm_count", 1).toString())
        editInterval.setText(prefs.getInt("interval_min", 5).toString())

        findViewById<Button>(R.id.btn_save).setOnClickListener {
            val hours = editHours.text.toString().toIntOrNull()?.coerceIn(1, 23) ?: 8
            val count = editCount.text.toString().toIntOrNull()?.coerceIn(1, 10) ?: 1
            val interval = editInterval.text.toString().toIntOrNull()?.coerceIn(0, 120) ?: 5
            prefs.edit()
                .putInt("base_hours", hours)
                .putInt("alarm_count", count)
                .putInt("interval_min", interval)
                .apply()
            Toast.makeText(this, getString(R.string.saved_toast), Toast.LENGTH_SHORT).show()
            finish()
        }

        findViewById<Button>(R.id.btn_pin_widget).setOnClickListener {
            val awm = getSystemService(AppWidgetManager::class.java)
            if (awm != null && awm.isRequestPinAppWidgetSupported) {
                awm.requestPinAppWidget(
                    ComponentName(this, ClockWidgetProvider::class.java), null, null
                )
            } else {
                Toast.makeText(this, getString(R.string.pin_widget_manual), Toast.LENGTH_LONG).show()
            }
        }

        findViewById<Button>(R.id.btn_open_clock).setOnClickListener {
            try {
                startActivity(Intent(AlarmClock.ACTION_SHOW_ALARMS))
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, getString(R.string.no_clock_app), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
