package com.sihaloho.aplikasigithubuser.remainder

import android.content.Context
import android.content.SharedPreferences

class Pref (context: Context) {
    companion object{
        const val PREFS_NAME ="reminder_pref"
        private const val REMINDER = "isRemind"
    }

    private val preference = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setReminder(value: ItemsRemainder){
        val editor = preference.edit()
        editor.putBoolean(REMINDER, value.reminder)
        editor.apply()
    }

    fun getReminder(): ItemsRemainder{
        val model = ItemsRemainder()
        model.reminder = preference.getBoolean(REMINDER, false)
        return model
    }
}