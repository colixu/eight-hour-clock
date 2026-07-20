package com.example.eighthourclock

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.provider.AlarmClock
import java.util.Calendar

/** 通过系统时钟 App 设置闹钟（MainActivity 和桌面小组件共用） */
object SystemAlarms {

    /** 设置 count 个闹钟，返回 成功数量 和 首个闹钟的时间文本 */
    fun set(context: Context, hours: Int, count: Int, intervalMin: Int): Pair<Int, String> {
        var firstTimeText = ""
        var successCount = 0

        for (i in 0 until count) {
            val target = Calendar.getInstance().apply {
                add(Calendar.HOUR_OF_DAY, hours)
                add(Calendar.MINUTE, i * intervalMin)
            }
            val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
                putExtra(AlarmClock.EXTRA_HOUR, target.get(Calendar.HOUR_OF_DAY))
                putExtra(AlarmClock.EXTRA_MINUTES, target.get(Calendar.MINUTE))
                val label = context.getString(R.string.app_name)
                putExtra(
                    AlarmClock.EXTRA_MESSAGE,
                    if (count > 1) "$label ${i + 1}/$count" else label
                )
                putExtra(AlarmClock.EXTRA_SKIP_UI, true)
                // 小组件是广播上下文，启动 Activity 需要这个标记
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            try {
                context.startActivity(intent)
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
        return Pair(successCount, firstTimeText)
    }
}
