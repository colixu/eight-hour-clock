package com.example.eighthourclock

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast

/**
 * 桌面小组件：一个 1x1 按钮。
 * 点它不启动任何界面（广播直接处理），真正零动画设闹钟。
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
            val intent = Intent(context, ClockWidgetProvider::class.java).apply {
                action = ACTION_SET_ALARM
            }
            val pi = PendingIntent.getBroadcast(
                context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            val views = RemoteViews(context.packageName, R.layout.widget_clock)
            views.setOnClickPendingIntent(R.id.widget_root, pi)
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
