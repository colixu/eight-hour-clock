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

        // 当前时间 + 8 小时
        val target = Calendar.getInstance().apply {
            add(Calendar.HOUR_OF_DAY, 8)
        }

        // 请系统时钟 App 设置闹钟（无需任何运行时权限，静默设置不弹界面）
        val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_HOUR, target.get(Calendar.HOUR_OF_DAY))
            putExtra(AlarmClock.EXTRA_MINUTES, target.get(Calendar.MINUTE))
            putExtra(AlarmClock.EXTRA_MESSAGE, getString(R.string.app_name))
            putExtra(AlarmClock.EXTRA_SKIP_UI, true)
        }

        try {
            startActivity(intent)
            val timeText = "%02d:%02d".format(
                target.get(Calendar.HOUR_OF_DAY),
                target.get(Calendar.MINUTE)
            )
            Toast.makeText(this, getString(R.string.alarm_set, timeText), Toast.LENGTH_LONG).show()
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, getString(R.string.no_clock_app), Toast.LENGTH_LONG).show()
        }

        // 设完即退，用户无感知
        finish()
    }
}
