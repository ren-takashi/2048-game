package com.neonhub.game

import android.content.Context
import android.content.SharedPreferences

class DataManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("NeonHubDB", Context.MODE_PRIVATE)

    var coins: Int
        get() = prefs.getInt("coins", 0)
        set(value) = prefs.edit().putInt("coins", value).apply()

    var diamonds: Int
        get() = prefs.getInt("diamonds", 0)
        set(value) = prefs.edit().putInt("diamonds", value).apply()
        
    var maxLevel: Int
        get() = prefs.getInt("maxLevel", 1)
        set(value) = prefs.edit().putInt("maxLevel", value).apply()
}
