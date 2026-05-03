package com.ki_bun.pioneer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ki_bun.pioneer.ui.theme.PioneerTheme
import com.ki_bun.pioneer.data.Item
import com.ki_bun.pioneer.component.ProgressCard
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

                val progressList by progressViewModel.progressList.collectAsState()
                var selectedItem by remember { mutableStateOf<Item?>(null) }
                var selectedIndex by remember { mutableIntStateOf(0)}

                if (showEditDialog && selectedItem != null) {
                    selectedItem?.let { item ->
                        EditDialog(
                            progressList = item,
                            onUpdate = { newItem ->
                                progressViewModel.updateItem(newItem)
                                showEditDialog = false
                            }
                        )
                    }
                }

                if (showDialog) {
                    InputDialog(progressViewModel = progressViewModel)
                }

                Scaffold(
                    floatingActionButton = {
                        FloatingActionButton(onClick = { showDialog = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.sharp_add_24),
                                contentDescription = "Add"
                            )
                        }
                    },
                    bottomBar = {
                        BottomAppBar(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            contentColor = MaterialTheme.colorScheme.onSurface,
                            actions = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Box(
                                            modifier = Modifier
                                                .background(color = if (selectedIndex == 0) { MaterialTheme.colorScheme.secondaryContainer } else Color.Transparent, shape = CircleShape)
                                                .width(60.dp)
                                                .height(35.dp)
                                        ) {
                                            IconButton(
                                                modifier = Modifier.width(60.dp),
                                                onClick = { selectedIndex = 0 },
                                            ) {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.home_24px),
                                                    contentDescription = "Home"
                                                )
                                            }
                                        }
                                        Text(text = "Home",
                                            fontSize = 12.sp)
                                    }
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Box(modifier = Modifier
                                            .background(color = if (selectedIndex == 1) { MaterialTheme.colorScheme.secondaryContainer } else Color.Transparent, shape = CircleShape)
                                            .width(60.dp)
                                            .height(35.dp)
                                        ) {
                                        IconButton(
                                            modifier = Modifier.width(60.dp),
                                            onClick = { selectedIndex = 1 }
                                        ) {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.settings_24px),
                                                    contentDescription = "Settings"
                                                )
                                            }
                                        }
                                        Text(text = "Settings",
                                            fontSize = 12.sp)
                                    }
                                }
                            }
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    LazyColumn(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        items(progressList) { item ->
                            ProgressCard(item,
                                onDelete = {
                                    progressViewModel.deleteItem(item)
                                },
                                onEdit = {
                                    selectedItem = item
                                    showEditDialog = true
                                },
                                progressViewModel = progressViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}