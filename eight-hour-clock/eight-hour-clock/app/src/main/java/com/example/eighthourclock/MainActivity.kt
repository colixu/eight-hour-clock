package com.example.eighthourclock

import android.app.Activity
import android.os.Bundle
import android.widget.Toast

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0) // 关掉进入动画

        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val count = prefs.getInt("alarm_count", 1)
        val intervalMin = prefs.getInt("interval_min", 5)

        // 长按菜单里的"快设 X 小时"会带 quick_hours 参数
        val quickFromString = intent.getStringExtra("quick_hours")?.toIntOrNull()
        val quickFromInt = intent.getIntExtra("quick_hours", -1)
        val hours = when {
            quickFromString != null && quickFromString > 0 -> quickFromString
            quickFromInt > 0 -> quickFromInt
            else -> prefs.getInt("base_hours", 8)
        }

        val (successCount, firstTimeText) = SystemAlarms.set(this, hours, count, intervalMin)
        val msg = when {
            successCount == 0 -> getString(R.string.no_clock_app)
            successCount == 1 -> getString(R.string.alarm_set, firstTimeText)
            else -> getString(R.string.alarm_set_multi, successCount, firstTimeText)
        }
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()

        finish()
        overridePendingTransition(0, 0) // 关掉退出动画
    }
}
