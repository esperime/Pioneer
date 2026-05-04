package com.ki_bun.pioneer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.ki_bun.pioneer.data.AppDatabase
import com.ki_bun.pioneer.data.ItemDao
import com.ki_bun.pioneer.ui.theme.PioneerTheme
import com.ki_bun.pioneer.ui.theme.ThemeMode
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val itemDao: ItemDao by lazy {
        AppDatabase.getDatabase(applicationContext).itemDao()
    }
    private val progressViewModel: ProgressViewModel by viewModels {
        ProgressViewModelFactory(itemDao)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var themeMode by remember { mutableStateOf(ThemeMode.AUTO) }
            var isThemeLoaded by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                lifecycleScope.launch {
                    loadThemeMode(this@MainActivity).collect { savedTheme ->
                        themeMode = savedTheme
                        isThemeLoaded = true
                    }
                }
            }

            if (isThemeLoaded) {
                PioneerTheme(themeMode = themeMode) {
                    MyAppNavHost(progressViewModel, themeMode = themeMode, onThemeModeChange = { selectedTheme ->
                        themeMode = selectedTheme
                        lifecycleScope.launch {
                            saveThemeMode(this@MainActivity, selectedTheme)
                        }
                    })
                }
            }
        }
    }
}