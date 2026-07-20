package com.example.eighthourclock

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.Toast
import java.util.Calendar

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

        var firstTimeText = ""
        var successCount = 0

        for (i in 0 until count) {
            val target = Calendar.getInstance().apply {
                add(Calendar.HOUR_OF_DAY, hours)
                add(Calendar.MINUTE, i * intervalMin)
            }
            val setIntent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
                putExtra(AlarmClock.EXTRA_HOUR, target.get(Calendar.HOUR_OF_DAY))
                putExtra(AlarmClock.EXTRA_MINUTES, target.get(Calendar.MINUTE))
                val label = getString(R.string.app_name)
                putExtra(
                    AlarmClock.EXTRA_MESSAGE,
                    if (count > 1) "$label ${i + 1}/$count" else label
                )
                putExtra(AlarmClock.EXTRA_SKIP_UI, true)
            }
            try {
                startActivity(setIntent)
                successCount++
                if (i == 0) {
                    firstTimeText = "%02d:%02d".format(
                        target.get(Calendar.HOUR_OF_DAY),
                        target.get(Calendar.MINUTE)
                    )
                }
            } catch (e: ActivityNotFoundException) {
                break
            }
        }

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
