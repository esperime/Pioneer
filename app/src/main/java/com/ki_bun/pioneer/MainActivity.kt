package com.ki_bun.pioneer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.ki_bun.pioneer.ui.theme.PioneerTheme
import com.ki_bun.pioneer.data.AppDatabase
import com.ki_bun.pioneer.data.ItemDao

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
            PioneerTheme {
                MyAppNavHost(progressViewModel)
            }
        }
    }
}