package com.ki_bun.pioneer.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ki_bun.pioneer.ui.theme.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")
val THEME_MODE_KEY = stringPreferencesKey("theme_mode_key")

suspend fun saveThemeMode(context: Context, themeMode: ThemeMode) {
    context.dataStore.edit { preferences ->
        preferences[THEME_MODE_KEY] = themeMode.name
    }
}

fun loadThemeMode(context: Context): Flow<ThemeMode> {
    return context.dataStore.data
        .map { preferences ->
            val savedTheme = preferences[THEME_MODE_KEY] ?: ThemeMode.AUTO.name
                ThemeMode.valueOf(savedTheme)
        }
}