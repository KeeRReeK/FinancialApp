package com.example.financialapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.financialapp.Fragments.SettingsFragment
import java.util.Calendar

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // Get an instance of the SettingsFragment
        val settingsFragment = SettingsFragment()

        // Call the UpdateTableDataBase() method
        settingsFragment.UpdateTableDataBase()

    // Get an instance of the AlarmManager
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    // Set the alarm to trigger every day at midnight
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.add(Calendar.DAY_OF_MONTH, 1)

    // Create a PendingIntent to broadcast the AlarmReceiver
    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent =
        PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

// Schedule the alarm
    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
    }
}