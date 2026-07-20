package com.example.eighthourclock

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast

/**
 * 桌面小组件：与图标一致的 1x1 按钮。
 * 点图标区域 = 设闹钟（广播直接处理，零界面）；
 * 点右下角"设置" = 进设置页。
 */
class ClockWidgetProvider : AppWidgetProvider() {

    companion object {
        const val ACTION_SET_ALARM = "com.example.eighthourclock.WIDGET_SET_ALARM"
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (id in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.widget_clock)

            // 点图标：设闹钟
            val tapIntent = Intent(context, ClockWidgetProvider::class.java).apply {
                action = ACTION_SET_ALARM
            }
            val tapPi = PendingIntent.getBroadcast(
                context, 0, tapIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_icon, tapPi)

            // 点"设置"小入口：打开设置页
            val settingsIntent = Intent(context, SettingsActivity::class.java)
            val settingsPi = PendingIntent.getActivity(
                context, 1, settingsIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_settings, settingsPi)

            appWidgetManager.updateAppWidget(id, views)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == ACTION_SET_ALARM) {
            val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
            val hours = prefs.getInt("base_hours", 8)
            val count = prefs.getInt("alarm_count", 1)
            val intervalMin = prefs.getInt("interval_min", 5)

            val (successCount, firstTimeText) =
                SystemAlarms.set(context, hours, count, intervalMin)
            val msg = when {
                successCount == 0 -> context.getString(R.string.no_clock_app)
                successCount == 1 -> context.getString(R.string.alarm_set, firstTimeText)
                else -> context.getString(R.string.alarm_set_multi, successCount, firstTimeText)
            }
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        }
    }
}
