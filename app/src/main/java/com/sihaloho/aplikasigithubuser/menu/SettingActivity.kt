package com.sihaloho.aplikasigithubuser.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sihaloho.aplikasigithubuser.R
import com.sihaloho.aplikasigithubuser.remainder.AlarmReceiver
import com.sihaloho.aplikasigithubuser.remainder.ItemsRemainder
import com.sihaloho.aplikasigithubuser.remainder.Pref
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {


    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var itemsRemainder: ItemsRemainder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        alarmReceiver = AlarmReceiver()
        val reminderPreference = Pref(this)
        if (reminderPreference.getReminder().reminder){
            sw_reminder.setChecked(true)
        } else{
            sw_reminder.setChecked(false)
        }

        sw_reminder.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                saveReminder(true)

                val onceTime = "09:00"
                val onceMessage = "Sudahkah melihat Github Hari ini?"
                alarmReceiver.setOneTimeAlarm(
                    this, AlarmReceiver.TYPE_ONE_TIME,
                    onceTime,
                    onceMessage
                )
            } else {
                saveReminder(false)
                alarmReceiver.cancelAlarm(
                    this,
                    AlarmReceiver.TYPE_ONE_TIME)
            }
        }

    }

    private fun saveReminder(bol: Boolean) {
        val reminderPreference = Pref(this)
        itemsRemainder = ItemsRemainder()

        itemsRemainder.reminder = bol

        reminderPreference.setReminder(itemsRemainder)
    }


}